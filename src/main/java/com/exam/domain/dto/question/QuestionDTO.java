package com.exam.domain.dto.question;

import com.exam.domain.dto.manage.GradeDTO;
import com.exam.domain.dto.manage.SubjectDTO;
import com.exam.domain.entity.manage.Grade;
import com.exam.domain.entity.manage.Subject;
import com.exam.domain.entity.question.Question;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 试题信息返回类
 *
 * @author sunc
 * @date 2020/3/14 13:18
 */

@Getter
public class QuestionDTO {
    private Long id;
    private Date createTime;
    private Date updateTime;

    private Integer level;
    private String type;
    private String tip;
    private String chapter;
    private String content;
    private String answer;
    private SubjectDTO subject;
    private GradeDTO grade;

    public QuestionDTO() {
    }

    public QuestionDTO(Question question, Subject subject, Grade grade, Boolean withAnswer) {
        this.id = question.getId();
        this.createTime = question.getCreateTime();
        this.updateTime = question.getUpdateTime();
        this.level = question.getLevel();
        this.type = question.getType();
        this.tip = question.getTip();
        this.chapter = question.getChapter();
        this.content = question.getContent();
        if (withAnswer) {
            this.answer = question.getAnswer();
        }
        this.subject = new SubjectDTO(subject);
        this.grade = new GradeDTO(grade);
    }

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.createTime = question.getCreateTime();
        this.updateTime = question.getUpdateTime();
        this.level = question.getLevel();
        this.type = question.getType();
        this.tip = question.getTip();
        this.chapter = question.getChapter();
        this.content = question.getContent();
        this.answer = question.getAnswer();
    }

    public QuestionDTO(Question question, List<Subject> subjects, List<Grade> grades) {
        this.id = question.getId();
        this.createTime = question.getCreateTime();
        this.updateTime = question.getUpdateTime();
        this.level = question.getLevel();
        this.type = question.getType();
        this.tip = question.getTip();
        this.chapter = question.getChapter();
        this.content = question.getContent();
        this.answer = question.getAnswer();
        this.subject = SubjectDTO.getSubject(question.getSubjectId(), subjects);
        this.grade = GradeDTO.getGrade(question.getGradeId(), grades);
    }

    public static List<QuestionDTO> toList(List<Question> questions, List<Subject> subjects, List<Grade> grades) {
        return questions.stream().map(q -> new QuestionDTO(q, subjects, grades)).collect(Collectors.toList());
    }

}
