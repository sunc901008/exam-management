package com.exam.domain.entity.question;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.domain.dto.question.PaperParam;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 试卷试题关联实体类
 *
 * @author sunc
 * @date 2020/3/14 11:44
 */
@Getter
@Setter
@TableName("sys_paper_question")
public class PaperQuestion extends BaseEntity {

    /**
     * 试卷id
     */
    @TableField("paper_id")
    private Long paperId;

    /**
     * 试题序号
     */
    private Integer index;

    /**
     * 试题内容
     */
    private String content;

    /**
     * 试题分值
     */
    private Integer score;

    /**
     * 试题答案
     */
    private String answer;

    public static PaperQuestion build(PaperParam.PaperQuestionParam paperQuestionParam, Question question) {
        PaperQuestion entity = new PaperQuestion();
        entity.setIndex(paperQuestionParam.getIndex());
        entity.setScore(paperQuestionParam.getScore());
        entity.setContent(question.getContent());
        entity.setAnswer(question.getAnswer());
        return entity;
    }

    public static PaperQuestion copy(PaperQuestion paperQuestion) {
        PaperQuestion entity = new PaperQuestion();
        entity.setId(paperQuestion.getId());
        entity.setVersion(paperQuestion.getVersion());
        return entity;
    }

}
