package com.exam.domain.entity.manage;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.domain.dto.manage.ClazzParam;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 班级表
 *
 * @author sunc
 * @date 2020/3/8 12:53
 */
@Getter
@Setter
@TableName("sys_class")
public class Clazz extends BaseEntity {

    private String name;
    /**
     * 年级
     */
    @TableField("grade_id")
    private Long gradeId;

    public Clazz() {
    }

    public Clazz(ClazzParam param) {
        this.setId(param.getId());
        this.name = param.getName();
        this.gradeId = param.getGradeId();
    }

    public static Clazz copy(Clazz subject) {
        Clazz entity = new Clazz();
        entity.setId(subject.getId());
        entity.setVersion(subject.getVersion());
        return entity;
    }

}
