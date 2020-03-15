package com.exam.domain.dto.manage;

import cn.hutool.core.util.StrUtil;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * 学生信息参数类
 *
 * @author sunc
 * @date 2020/3/7 13:45
 */
@Setter
@Getter
public class StudentParam extends BaseDTO {

    private Long id;
    private String studentNumber;
    private String name;
    private String sex;
    private Integer age;
    private String password;
    private Long classId;

    public static StudentParam select(HttpServletRequest request) {
        StudentParam param = new StudentParam();
        String name = request.getParameter("name");
        if (StrUtil.isNotBlank(name)) {
            param.name = name.trim();
        }
        return param;
    }

    public static StudentParam build(String data) throws LocalException {
        StudentParam param = StudentParam.init(data, StudentParam.class);
        param.check();
        return param;
    }

    /**
     * 参数校验
     */
    @Override
    protected void check() throws LocalException {
        if (StrUtil.isBlank(studentNumber)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "studentNumber:" + studentNumber);
        }
        if (StrUtil.isBlank(name)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "name:" + name);
        }
        if (StrUtil.isBlank(sex)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "sex:" + sex);
        }
        if (classId == null || classId <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "classId:" + classId);
        }
    }

}
