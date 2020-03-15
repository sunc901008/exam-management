package com.exam.domain.entity.manage;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.base.policy.PasswordPolicy;
import com.exam.domain.dto.manage.StudentParam;
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
@TableName("sys_student")
public class Student extends BaseEntity {
    /**
     * 学号
     */
    @TableField("student_number")
    private String studentNumber;
    private String name;
    private String sex;
    private Integer age;
    private String password;
    /**
     * 班号
     */
    @TableField("class_id")
    private Long classId;
    /**
     * 软删除标识
     */
    @TableField("is_delete")
    private Boolean deleted;

    public Student() {
    }

    public Student(StudentParam param) {
        this.setId(param.getId());
        this.studentNumber = param.getStudentNumber();
        this.name = param.getName();
        this.sex = param.getSex();
        this.age = param.getAge();
        // 没有密码时初始化密码
        String password = param.getPassword();
        if (StrUtil.isBlank(password)) {
            password = studentNumber;
        }
        this.password = PasswordPolicy.encrypt(password);
        this.classId = param.getClassId();
    }

    /**
     * 从 Excel 中读取内容, 转换为 Student 对象. (Excel 列顺序固定: name, subjectId, sex, age, gradeId)
     */
    public static List<Student> parseList(List<List<String>> contents) {
        return contents.stream().map(Student::parse).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static Student parse(List<String> content) {
        Student student = new Student();
        int index = 0;
        String name = content.get(index++);
        if (StrUtil.isBlank(name)) {
            return null;
        }
        student.name = name;

        String studentNumber = content.get(index++);
        if (StrUtil.isBlank(studentNumber)) {
            return null;
        }
        student.studentNumber = studentNumber;

        String sex = content.get(index++);
        if (StrUtil.isBlank(sex)) {
            return null;
        }
        student.sex = sex;

        String age = content.get(index++);
        if (StrUtil.isBlank(age)) {
            return null;
        }
        student.age = Integer.parseInt(age);

        String classId = content.get(index);
        if (StrUtil.isBlank(classId)) {
            return null;
        }
        student.classId = Long.parseLong(classId);
        return student;
    }

    public static Student copy(Student student) {
        Student entity = new Student();
        entity.setId(student.getId());
        entity.setVersion(student.getVersion());
        return entity;
    }

}
