package com.exam.domain.entity.question;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.domain.dto.question.PaperParam;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 试卷实体类
 *
 * @author sunc
 * @date 2020/3/14 11:44
 */
@Getter
@Setter
@TableName("sys_paper")
public class Paper extends BaseEntity {

    /**
     * 难度级别, 越大越难
     */
    private Integer level;

    /**
     * 所属学科
     */
    @TableField("subject_id")
    private Long subjectId;

    /**
     * 试卷标题
     */
    private String name;

    /**
     * 年级, 表示是哪个年级的试卷
     */
    @TableField("grade_id")
    private Long gradeId;

    /**
     * 软删除标识
     */
    @TableField("is_delete")
    private Boolean deleted;

    /**
     * 已发布标识(已发布的试卷不能修改)
     */
    @TableField("is_publish")
    private Boolean published;

    public Paper() {
    }

    public Paper(PaperParam param) {
        this.subjectId = param.getSubjectId();
        this.level = param.getLevel();
        this.name = param.getName();
        this.gradeId = param.getGradeId();
    }

    public static Paper copy(Paper question) {
        Paper entity = new Paper();
        entity.setId(question.getId());
        entity.setVersion(question.getVersion());
        return entity;
    }
}
