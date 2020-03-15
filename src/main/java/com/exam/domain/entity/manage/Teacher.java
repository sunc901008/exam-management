package com.exam.domain.entity.manage;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.base.policy.PasswordPolicy;
import com.exam.domain.dto.manage.TeacherParam;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sunc
 * @date 2020/3/7 13:39
 */
@Getter
@Setter
@TableName("sys_teacher")
public class Teacher extends BaseEntity {
    /**
     * 编号
     */
    @TableField("teacher_number")
    private String teacherNumber;
    private String name;
    private String sex;
    private Integer age;
    private String password;
    /**
     * 所教科目
     */
    @TableField("subject_id")
    private Long subjectId;
    /**
     * 软删除标识
     */
    @TableField("is_delete")
    private Boolean deleted;

    public Teacher() {
    }

    public Teacher(TeacherParam param) {
        this.setId(param.getId());
        this.teacherNumber = param.getTeacherNumber();
        this.name = param.getName();
        this.sex = param.getSex();
        this.age = param.getAge();
        // 没有密码时初始化密码
        String password = param.getPassword();
        if (StrUtil.isBlank(password)) {
            password = teacherNumber;
        }
        this.password = PasswordPolicy.encrypt(password);
        this.subjectId = param.getSubjectId();
    }

    /**
     * 从 Excel 中读取内容, 转换为 Teacher 对象. (Excel 列顺序固定: name, tchNum, sex, age, subjectId)
     */
    public static List<Teacher> parseList(List<List<String>> contents) {
        return contents.stream().map(Teacher::parse).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static Teacher parse(List<String> content) {
        Teacher teacher = new Teacher();
        int index = 0;
        String name = content.get(index++);
        if (StrUtil.isBlank(name)) {
            return null;
        }
        teacher.name = name;

        String teacherNumber = content.get(index++);
        if (StrUtil.isBlank(teacherNumber)) {
            return null;
        }
        teacher.teacherNumber = teacherNumber;

        String sex = content.get(index++);
        if (StrUtil.isBlank(sex)) {
            return null;
        }
        teacher.sex = sex;

        String age = content.get(index++);
        if (StrUtil.isBlank(age)) {
            return null;
        }
        teacher.age = Integer.parseInt(age);

        String subjectId = content.get(index);
        if (StrUtil.isBlank(subjectId)) {
            return null;
        }
        teacher.subjectId = Long.parseLong(subjectId);
        return teacher;
    }

    public static Teacher copy(Teacher teacher) {
        Teacher entity = new Teacher();
        entity.setId(teacher.getId());
        entity.setVersion(teacher.getVersion());
        return entity;
    }

}
