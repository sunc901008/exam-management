package com.exam.response;

import com.alibaba.fastjson.JSONObject;
import com.exam.base.CommonUtils;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 接口返回基类
 *
 * @author sunc
 * @date 2020/3/7 14:11
 */

public class BaseResponse {
    public boolean success = true;
    public String exception = "";
    public int errCode = 0;
    public JSONObject msgParams = new JSONObject();
    public Object data;

    public static BaseResponse success() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.data = true;
        return baseResponse;
    }

    /**
     * 正常返回数据
     *
     * @param data 返回数据
     * @return BaseResponse
     */
    public static BaseResponse success(Object data) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.data = data;
        return baseResponse;
    }

    /**
     * 出现异常时构造返回
     *
     * @param e 自定义异常
     */
    public static BaseResponse fail(LocalException e) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.success = false;
        baseResponse.errCode = e.errCode;
        baseResponse.exception = e.message;
        baseResponse.msgParams = e.msgParams;
        return baseResponse;
    }

    /**
     * 服务异常/未知异常时根据状态返回数据
     *
     * @param e 异常
     */
    public static BaseResponse fail(Exception e) {
        int errCode = ExceptionCode.SERVER_ERROR;
        String msg;
        if (e instanceof NoHandlerFoundException) {
            msg = "Invalid Request Url";
            errCode = ExceptionCode.NOT_FOUND;
        } else {
            msg = e.getMessage();
            String web = "org.springframework.web";
            String http = "org.springframework.http";
            String exceptionName = e.getClass().getName();
            if (exceptionName.startsWith(web)) {
                errCode = ExceptionCode.PARAM_ERROR;
            } else if (exceptionName.startsWith(http)) {
                errCode = ExceptionCode.METHOD_ERROR;
            }
        }

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.success = false;
        baseResponse.exception = msg;
        baseResponse.errCode = errCode;
        return baseResponse;
    }

    /**
     * 出现绑定错误时构造返回实例
     *
     * @param exception 异常
     */
    public static BaseResponse fail(String exception) {
        return fail(ExceptionCode.NOT_DEFINE_ERROR, exception);
    }

    /**
     * @param exception 异常
     */
    public static BaseResponse fail(int errCode, String exception) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.success = false;
        baseResponse.errCode = errCode;
        baseResponse.exception = exception;

        return baseResponse;
    }

    @Override
    public String toString() {
        return CommonUtils.toJsonString(this, true);
    }

}