package com.exam.domain.dto.question;

import cn.hutool.core.util.StrUtil;
import com.exam.domain.dto.BaseDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * 试题信息参数类
 *
 * @author sunc
 * @date 2020/3/14 13:18
 */

@Setter
@Getter
public class QuestionParam extends BaseDTO {

    private Long id;
    private Integer level;
    private Long subjectId;
    private String type;
    private String tip;
    private String chapter;
    private Long gradeId;
    private String content;
    private String answer;

    public static QuestionParam select(HttpServletRequest request) {
        QuestionParam param = new QuestionParam();
        String level = request.getParameter("level");
        if (StrUtil.isNotBlank(level)) {
            param.level = Integer.parseInt(level);
        }
        String subjectId = request.getParameter("subjectId");
        if (StrUtil.isNotBlank(subjectId)) {
            param.subjectId = Long.parseLong(subjectId);
        }
        String type = request.getParameter("type");
        if (StrUtil.isNotBlank(type)) {
            param.type = type;
        }
        String tip = request.getParameter("tip");
        if (StrUtil.isNotBlank(tip)) {
            param.tip = tip;
        }
        String chapter = request.getParameter("chapter");
        if (StrUtil.isNotBlank(chapter)) {
            param.chapter = chapter;
        }
        return param;
    }

    public static QuestionParam build(String data) throws LocalException {
        QuestionParam param = QuestionParam.init(data, QuestionParam.class);
        param.check();
        return param;
    }

    public static QuestionParam update(String data) throws LocalException {
        QuestionParam param = QuestionParam.init(data, QuestionParam.class);
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
        if (StrUtil.isBlank(type)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "type:" + type);
        }
        if (gradeId == null || gradeId <= 0) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "gradeId:" + gradeId);
        }
        if (StrUtil.isBlank(content)) {
            throw new LocalException(ExceptionCode.PARAM_ERROR, "content:" + content);
        }
    }

}
