package com.exam.domain.entity.manage;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.domain.dto.manage.TeacherClazzParam;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sunc
 * @date 2020/3/7 13:39
 */
@Getter
@Setter
@TableName("sys_teacher_class")
public class TeacherClazz extends BaseEntity {
    @TableField("teacher_number")
    private String teacherNumber;
    /**
     * 所教班级
     */
    @TableField("class_id")
    private Long classId;

    public TeacherClazz() {
    }

    public TeacherClazz(TeacherClazzParam param) {
        this.teacherNumber = param.getTeacherNumber();
        this.classId = param.getClassId();
    }

}
