package com.exam.domain.dto.manage;

import cn.hutool.core.util.StrUtil;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

/**
 * 学生信息参数类
 *
 * @author sunc
 * @date 2020/3/7 13:45
 */
@Setter
@Getter
public class TeacherClazzParam extends BaseDTO {

    private String teacherNumber;
    private Long classId;

    public static TeacherClazzParam build(String data) throws LocalException {
        TeacherClazzParam param = TeacherClazzParam.init(data, TeacherClazzParam.class);
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
        if (classId == null || classId <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "classId:" + classId);
        }
    }

}
