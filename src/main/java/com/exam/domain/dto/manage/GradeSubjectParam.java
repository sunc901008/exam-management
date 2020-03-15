package com.exam.domain.dto.manage;

import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 年级科目关联信息参数类
 *
 * @author sunc
 * @date 2020/3/8 12:58
 */

@Setter
@Getter
public class GradeSubjectParam extends BaseDTO {

    private List<Long> gradeIds;
    private List<Long> subjectIds;

    public static GradeSubjectParam build(String data) throws LocalException {
        GradeSubjectParam param = GradeSubjectParam.init(data, GradeSubjectParam.class);
        param.check();
        return param;
    }

    /**
     * 参数校验
     */
    @Override
    protected void check() throws LocalException {
        if (gradeIds == null || gradeIds.isEmpty()) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "gradeIds");
        }
        if (subjectIds == null || subjectIds.isEmpty()) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "subjectIds");
        }
    }

}
