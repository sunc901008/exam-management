package com.exam.domain.entity.manage;

import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.domain.dto.manage.SubjectParam;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 科目表
 *
 * @author sunc
 * @date 2020/3/8 12:53
 */
@Getter
@Setter
@TableName("sys_subject")
public class Subject extends BaseEntity {

    private String name;

    public Subject() {
    }

    public Subject(SubjectParam param) {
        this.name = param.getName();
    }

    public static Subject copy(Subject subject) {
        Subject entity = new Subject();
        entity.setId(subject.getId());
        entity.setVersion(subject.getVersion());
        return entity;
    }

}
