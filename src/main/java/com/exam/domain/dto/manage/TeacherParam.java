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
public class TeacherParam extends BaseDTO {

    private Long id;
    private String teacherNumber;
    private String name;
    private String sex;
    private Integer age;
    private String password;
    private Long subjectId;

    public static TeacherParam select(HttpServletRequest request) {
        TeacherParam param = new TeacherParam();
        String name = request.getParameter("name");
        if (StrUtil.isNotBlank(name)) {
            param.name = name.trim();
        }
        return param;
    }

    public static TeacherParam build(String data) throws LocalException {
        TeacherParam param = TeacherParam.init(data, TeacherParam.class);
        param.check();
        return param;
    }

    /**
     * 参数校验
     */
    @Override
    protected void check() throws LocalException {
        if (StrUtil.isBlank(teacherNumber)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "teacherNumber:" + teacherNumber);
        }
        if (StrUtil.isBlank(name)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "name:" + name);
        }
        if (StrUtil.isBlank(sex)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "sex:" + sex);
        }
    }

}
