package com.exam.domain.dto.question;

import com.exam.domain.dto.manage.GradeDTO;
import com.exam.domain.dto.manage.SubjectDTO;
import com.exam.domain.entity.manage.Grade;
import com.exam.domain.entity.manage.Subject;
import com.exam.domain.entity.question.Paper;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 试卷信息返回类
 *
 * @author sunc
 * @date 2020/3/14 13:17
 */

@Getter
public class PaperDTO {
    private Long id;
    private Date createTime;
    private Date updateTime;

    private Integer level;
    private SubjectDTO subject;
    private String name;
    private GradeDTO grade;

    private List<PaperQuestionDTO> questions;

    public PaperDTO() {
    }

    public PaperDTO(int level, Subject subject, Grade grade, List<PaperQuestionDTO> questions) {
        this.level = level;
        this.subject = new SubjectDTO(subject);
        this.grade = new GradeDTO(grade);
        this.questions = questions;
    }

    public PaperDTO(Paper paper, Subject subject, Grade grade, List<PaperQuestionDTO> questions) {
        this.id = paper.getId();
        this.createTime = paper.getCreateTime();
        this.updateTime = paper.getUpdateTime();

        this.level = paper.getLevel();
        this.subject = new SubjectDTO(subject);
        this.grade = new GradeDTO(grade);
        this.name = paper.getName();
        this.questions = questions;
    }

    public PaperDTO(Paper paper) {
        this.id = paper.getId();
        this.createTime = paper.getCreateTime();
        this.updateTime = paper.getUpdateTime();

        this.level = paper.getLevel();
        this.name = paper.getName();
    }

    public PaperDTO(Paper paper, List<Subject> subjects, List<Grade> grades) {
        this.id = paper.getId();
        this.createTime = paper.getCreateTime();
        this.updateTime = paper.getUpdateTime();

        this.level = paper.getLevel();
        this.name = paper.getName();
        this.subject = SubjectDTO.getSubject(paper.getSubjectId(), subjects);
        this.grade = GradeDTO.getGrade(paper.getGradeId(), grades);
    }

    public static List<PaperDTO> toList(List<Paper> papers, List<Subject> subjects, List<Grade> grades) {
        return papers.stream().map(p -> new PaperDTO(p, subjects, grades)).collect(Collectors.toList());
    }

    public static List<PaperDTO> toList(List<Paper> papers) {
        return papers.stream().map(PaperDTO::new).collect(Collectors.toList());
    }

}
