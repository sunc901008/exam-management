package com.exam.domain.entity.question;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.domain.dto.question.PaperParam;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 学生成绩实体类
 *
 * @author sunc
 * @date 2020/3/15 15:59
 */

@Getter
@Setter
@TableName("sys_exam_result")
public class ExamResult extends BaseEntity {

    @TableField("student_number")
    private String studentNumber;
    /**
     * 试卷id
     */
    @TableField("paper_id")
    private Long paperId;

    private Integer score;

    public ExamResult() {
    }

    public ExamResult(String studentNumber, Long paperId, Integer score) {
        this.studentNumber = studentNumber;
        this.paperId = paperId;
        this.score = score;
    }

    public static ExamResult copy(ExamResult question) {
        ExamResult entity = new ExamResult();
        entity.setId(question.getId());
        entity.setVersion(question.getVersion());
        return entity;
    }
}
