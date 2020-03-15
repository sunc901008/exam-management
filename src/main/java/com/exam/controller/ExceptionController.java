package com.exam.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.Objects;

/**
 * 异常控制类
 *
 * @author sunc
 * @date 2020/3/8 14:16
 */

@RestControllerAdvice
public class ExceptionController {
    private static final Logger logger = Logger.getLogger(ExceptionController.class);

    /**
     * 出现自定义异常时构造返回实例
     *
     * @param e 自定义异常类
     * @return baseResponse
     */
    @ExceptionHandler(LocalException.class)
    @ResponseBody
    public BaseResponse exception(LocalException e) {
        logger.error(ExceptionUtil.stacktraceToString(e));
        return BaseResponse.fail(e);
    }

    /**
     * 出现绑定错误时构造返回实例
     *
     * @param e 绑定错误类
     * @return baseResponse
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public BaseResponse exception(BindException e) {
        logger.error(ExceptionUtil.stacktraceToString(e));
        String exception = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return BaseResponse.fail(exception);
    }

    /**
     * springframework / java.sql 的数据库操作出现异常时构造返回实例
     *
     * @param e springframework.dao 数据库操作异常类 / java.sql 数据库异常类
     * @return baseResponse
     */
    @ExceptionHandler({DataAccessException.class, SQLException.class})
    @ResponseBody
    public BaseResponse exception(Exception e) {
        logger.error(ExceptionUtil.stacktraceToString(e));
        return BaseResponse.fail(ExceptionCode.SQL_ERROR, e.getMessage());
    }

    /**
     * 出现未知异常时构造返回实例
     *
     * @param e Exception 未知异常
     * @return baseResponse
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse unknownException(Exception e) {
        logger.error(ExceptionUtil.stacktraceToString(e));
        return BaseResponse.fail(e);
    }

}
