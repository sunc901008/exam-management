package com.exam.domain.dto.question;

import cn.hutool.core.util.StrUtil;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

/**
 * 统计某学生的成绩
 * 哪个学生的哪次考试
 *
 * @author sunc
 * @date 2020/3/15 15:37
 */

@Setter
@Getter
public class PaperScoreParam extends BaseDTO {

    private Long paperId;
    private String studentNumber;

    public static PaperScoreParam build(String data) throws LocalException {
        PaperScoreParam param = PaperScoreParam.init(data, PaperScoreParam.class);
        param.check();
        return param;
    }

    /**
     * 参数校验
     */
    @Override
    protected void check() throws LocalException {
        if (paperId == null || paperId <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "paperId:" + paperId);
        }
        if (StrUtil.isBlank(studentNumber)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "studentNumber:" + studentNumber);
        }
    }

}
