package com.exam.domain.dto.question;

import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

/**
 * 试卷信息参数类
 *
 * @author sunc
 * @date 2020/3/14 13:18
 */

@Setter
@Getter
public class PaperReplaceParam extends BaseDTO {

    /**
     * 试卷id
     */
    private Long id;
    /**
     * 试题序号
     */
    private Integer index;
    /**
     * 试题 id
     */
    private Long questionId;
    /**
     * 试题分值
     */
    private Integer score;

    public static PaperReplaceParam build(String data) throws LocalException {
        PaperReplaceParam param = PaperReplaceParam.init(data, PaperReplaceParam.class);
        param.check();
        return param;
    }

    /**
     * 参数校验
     */
    @Override
    protected void check() throws LocalException {
        if (index == null || index <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "index:" + index);
        }
        if (id == null || id <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "id:" + id);
        }
        if (questionId == null || questionId <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "questionId:" + questionId);
        }
        if (score == null || score <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "score:" + score);
        }
    }

}
