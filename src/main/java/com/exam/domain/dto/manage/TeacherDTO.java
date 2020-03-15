package com.exam.domain.dto.manage;

import com.exam.domain.entity.manage.Subject;
import com.exam.domain.entity.manage.Teacher;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生信息返回类
 *
 * @author sunc
 * @date 2020/3/7 13:45
 */
@Getter
public class TeacherDTO {
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String teacherNumber;
    private String name;
    private String sex;
    private Integer age;
    private SubjectDTO subject;

    public TeacherDTO() {
    }

    public TeacherDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.createTime = teacher.getCreateTime();
        this.updateTime = teacher.getUpdateTime();

        this.teacherNumber = teacher.getTeacherNumber();
        this.name = teacher.getName();
        this.sex = teacher.getSex();
        this.age = teacher.getAge();
    }

    public TeacherDTO(Teacher teacher, List<Subject> subjects) {
        this.id = teacher.getId();
        this.createTime = teacher.getCreateTime();
        this.updateTime = teacher.getUpdateTime();

        this.teacherNumber = teacher.getTeacherNumber();
        this.name = teacher.getName();
        this.sex = teacher.getSex();
        this.age = teacher.getAge();
        this.subject = SubjectDTO.getSubject(teacher.getSubjectId(), subjects);
    }

    public static List<TeacherDTO> toList(List<Teacher> teachers, List<Subject> subjects) {
        return teachers.stream().map(t -> new TeacherDTO(t, subjects)).collect(Collectors.toList());
    }

    public static TeacherDTO test() {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.id = 9999L;
        teacherDTO.name = "test";
        return teacherDTO;
    }

}
