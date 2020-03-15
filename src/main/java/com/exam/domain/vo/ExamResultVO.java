package com.exam.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.exam.domain.entity.manage.Clazz;
import com.exam.domain.entity.manage.Grade;
import com.exam.domain.entity.manage.Subject;
import com.exam.domain.entity.question.Paper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 成绩排名
 *
 * @author sunc
 * @date 2020/3/15 16:29
 */
@Getter
@Setter
public class ExamResultVO {
    @TableField("paper_id")
    private Long paperId;
    @TableField("paper_name")
    private String paperName;

    @TableField("grade_id")
    private Long gradeId;
    @TableField("grade_name")
    private String gradeName;

    @TableField("class_id")
    private Long classId;
    @TableField("class_name")
    private String className;

    @TableField("student_number")
    private String studentNumber;
    @TableField("student_name")
    private String studentName;

    @TableField("subject_name")
    private String subjectName;

    private Integer score;

    public static void update(List<ExamResultVO> examResults, Paper paper, String subjectName, Clazz clazz, Grade grade) {
        for (ExamResultVO e : examResults) {
            e.paperId = paper.getId();
            e.paperName = paper.getName();
            e.gradeId = grade.getId();
            e.gradeName = grade.getName();
            if (clazz != null) {
                e.classId = clazz.getId();
                e.className = clazz.getName();
            }
            e.subjectName = subjectName;
        }
    }

    public static void update(List<ExamResultVO> examResults, Paper paper, String subjectName, Grade grade) {
        update(examResults, paper, subjectName, null, grade);
    }

}
