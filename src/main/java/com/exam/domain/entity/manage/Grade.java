package com.exam.domain.entity.manage;

import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.domain.dto.manage.GradeParam;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 年级表
 *
 * @author sunc
 * @date 2020/3/8 12:53
 */
@Getter
@Setter
@TableName("sys_grade")
public class Grade extends BaseEntity {

    private String name;

    public Grade() {
    }

    public Grade(GradeParam param) {
        this.setId(param.getId());
        this.name = param.getName();
    }

    public static Grade copy(Grade grade) {
        Grade entity = new Grade();
        entity.setId(grade.getId());
        entity.setVersion(grade.getVersion());
        return entity;
    }

}
