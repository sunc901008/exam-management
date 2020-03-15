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
 * 试卷信息参数类
 *
 * @author sunc
 * @date 2020/3/14 13:18
 */

@Setter
@Getter
public class PaperParam extends BaseDTO {

    private Long id;
    private Integer level;
    private Long subjectId;
    private String name;
    private Long gradeId;
    private List<PaperQuestionParam> questions;

    @Setter
    @Getter
    public class PaperQuestionParam extends BaseDTO {

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

        @Override
        protected void check() throws LocalException {
            if (index == null || index <= 0) {
                throw new LocalException(ExceptionCode.PARAM_ERROR, "index:" + index);
            }
            if (score == null || score <= 0) {
                throw new LocalException(ExceptionCode.PARAM_ERROR, "score:" + score);
            }
        }
    }

    public static PaperParam select(HttpServletRequest request) {
        PaperParam param = new PaperParam();
        String level = request.getParameter("level");
        if (StrUtil.isNotBlank(level)) {
            param.level = Integer.parseInt(level);
        }
        String subjectId = request.getParameter("subjectId");
        if (StrUtil.isNotBlank(subjectId)) {
            param.subjectId = Long.parseLong(subjectId.trim());
        }
        String name = request.getParameter("name");
        if (StrUtil.isNotBlank(name)) {
            param.name = name;
        }
        return param;
    }

    public static PaperParam build(String data) throws LocalException {
        PaperParam param = PaperParam.init(data, PaperParam.class);
        param.check();
        return param;
    }

    public static PaperParam update(String data) throws LocalException {
        PaperParam param = PaperParam.init(data, PaperParam.class);
        if (param.id == null || param.id == 0L) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR);
        }
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
        if (StrUtil.isBlank(name)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "name:" + name);
        }
        if (gradeId == null || gradeId <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "gradeId:" + gradeId);
        }
        if (questions == null || questions.isEmpty()) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "questions");
        }
        long distinct = questions.stream().map(PaperQuestionParam::getIndex).distinct().count();
        if (distinct < questions.size()) {
            throw new LocalException(ExceptionCode.SAME_INDEX_ERROR);
        }
        for (PaperQuestionParam q : questions) {
            q.check();
        }
    }

}
