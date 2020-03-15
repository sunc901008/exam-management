package com.exam.domain.dto.manage;

import com.exam.domain.entity.manage.Student;
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
public class StudentDTO {
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String studentNumber;
    private String name;
    private String sex;
    private Integer age;
    private Long classId;

    public StudentDTO() {
    }

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.createTime = student.getCreateTime();
        this.updateTime = student.getUpdateTime();

        this.studentNumber = student.getStudentNumber();
        this.name = student.getName();
        this.sex = student.getSex();
        this.age = student.getAge();
        this.classId = student.getClassId();
    }

    public static List<StudentDTO> toList(List<Student> students) {
        return students.stream().map(StudentDTO::new).collect(Collectors.toList());
    }

    public static StudentDTO test() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.id = 9999L;
        studentDTO.name = "test";
        return studentDTO;
    }

}
