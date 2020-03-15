package com.exam.domain.entity.question;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.domain.dto.question.QuestionParam;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 试题实体类
 *
 * @author sunc
 * @date 2020/3/14 11:44
 */
@Getter
@Setter
@TableName("sys_question")
public class Question extends BaseEntity {

    /**
     * 难度级别, 越大越难
     */
    private Integer level;

    /**
     * 所属学科编号
     */
    @TableField("subject_id")
    private Long subjectId;

    /**
     * 试题类型
     */
    private String type;

    /**
     * 知识点
     */
    private String tip;

    /**
     * 章节
     */
    private String chapter;

    /**
     * 年级编号, 表示是哪个年级的试题
     */
    @TableField("grade_id")
    private Long gradeId;

    /**
     * 试题内容
     */
    private String content;

    /**
     * 试题答案
     */
    private String answer;

    public Question() {
    }

    public Question(QuestionParam param) {
        this.subjectId = param.getSubjectId();
        this.level = param.getLevel();
        this.type = param.getType();
        this.tip = param.getTip();
        this.chapter = param.getChapter();
        this.gradeId = param.getGradeId();
        this.content = param.getContent();
        this.answer = param.getAnswer();
    }

    public static Question copy(Question question) {
        Question entity = new Question();
        entity.setId(question.getId());
        entity.setVersion(question.getVersion());
        return entity;
    }

    public boolean equals(Question question) {
        return this.getId().equals(question.getId());
    }

}
