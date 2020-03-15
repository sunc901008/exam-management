package com.exam.domain.dto.question;

import cn.hutool.core.util.StrUtil;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 试卷答案信息参数类
 *
 * @author sunc
 * @date 2020/3/14 13:18
 */

@Setter
@Getter
public class PaperAnswerParam extends BaseDTO {

    private String studentNumber;
    private Long paperId;
    private List<QuestionAnswerParam> answers;

    @Setter
    @Getter
    public class QuestionAnswerParam extends BaseDTO {
        private Integer index;
        private String answer;

        @Override
        protected void check() throws LocalException {
            if (index == null || index <= 0) {
                throw new LocalException(ExceptionCode.PARAM_ERROR, "index:" + index);
            }
        }
    }

    public static PaperAnswerParam build(String data) throws LocalException {
        PaperAnswerParam param = PaperAnswerParam.init(data, PaperAnswerParam.class);
        param.check();
        return param;
    }

    public static PaperAnswerParam select(HttpServletRequest request) throws LocalException {
        PaperAnswerParam param = new PaperAnswerParam();

        String paperId = request.getParameter("paperId");
        if (StrUtil.isBlank(paperId)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "paperId:" + paperId);
        }
        param.paperId = Long.parseLong(paperId.trim());
        String studentNumber = request.getParameter("studentNumber");
        if (StrUtil.isBlank(studentNumber)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "studentNumber:" + studentNumber);
        }
        param.studentNumber = studentNumber;
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
        if (paperId == null || paperId <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "paperId:" + paperId);
        }
        if (answers == null || answers.isEmpty()) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "answers");
        }
        for (QuestionAnswerParam answer : answers) {
            answer.check();
        }
    }

}
