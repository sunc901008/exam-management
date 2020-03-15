package com.exam.domain.dto.question;

import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 自动生成试卷参数类
 * <p>
 * 1. 什么难度的试卷
 * 2. 各类型题目数量
 * 3. 什么科目的试卷
 * 4. 什么年级的试卷
 *
 * @author sunc
 * @date 2020/3/14 13:18
 */

@Setter
@Getter
public class AutoPaperParam extends BaseDTO {

    private Integer level;
    private Long subjectId;
    private Long gradeId;
    private List<QuestionScoreParam> params;

    public static AutoPaperParam build(String data) throws LocalException {
        AutoPaperParam param = AutoPaperParam.init(data, AutoPaperParam.class);
        param.check();
        return param;
    }

    /**
     * 参数校验
     */
    @Override
    protected void check() throws LocalException {
        if (subjectId == null || subjectId <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "subjectId:" + subjectId);
        }
        if (gradeId == null || gradeId <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "gradeId:" + gradeId);
        }
        if (params == null || params.isEmpty()) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "params");
        }
    }

}
