package com.exam.domain.dto.question;

import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sunc
 * @date 2020/3/15 15:37
 */

@Setter
@Getter
public class AnswerScoreParam extends BaseDTO {

    /**
     * sys_paper_answer id
     */
    private Long id;
    private Integer score;

    public static AnswerScoreParam build(String data) throws LocalException {
        AnswerScoreParam param = AnswerScoreParam.init(data, AnswerScoreParam.class);
        param.check();
        return param;
    }

    /**
     * 参数校验
     */
    @Override
    protected void check() throws LocalException {
        if (id == null || id <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "id:" + id);
        }
        if (score == null || score < 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "score:" + score);
        }
    }

}
