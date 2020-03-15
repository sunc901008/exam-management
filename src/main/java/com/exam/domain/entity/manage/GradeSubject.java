package com.exam.domain.entity.manage;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 年级科目关联表
 *
 * @author sunc
 * @date 2020/3/8 12:53
 */
@Getter
@Setter
@TableName("sys_grade_subject")
public class GradeSubject extends BaseEntity {

    /**
     * 年级
     */
    @TableField("grade_id")
    private Long gradeId;
    /**
     * 学科
     */
    @TableField("subject_id")
    private Long subjectId;

    public GradeSubject() {
    }

    public GradeSubject(Long gradeId, Long subjectId) {
        this.gradeId = gradeId;
        this.subjectId = subjectId;
    }
}
