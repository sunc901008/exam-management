package com.exam.domain.dto.uc;

import com.exam.base.Constant;
import com.exam.base.privilege.Role;
import com.exam.domain.dto.manage.ManagerDTO;
import com.exam.domain.dto.manage.StudentDTO;
import com.exam.domain.dto.manage.TeacherDTO;
import com.exam.domain.entity.manage.Manager;
import com.exam.domain.entity.manage.Student;
import com.exam.domain.entity.manage.Teacher;
import lombok.Getter;

/**
 * @author sunc
 * @date 2020/3/7 14:04
 */
@Getter
public class UserDTO {
    private String role;
    private StudentDTO student;
    private TeacherDTO teacher;
    private ManagerDTO manager;

    public static UserDTO buildStudent(Student student) {
        UserDTO userDTO = new UserDTO();
        userDTO.role = Role.STUDENT;
        userDTO.student = new StudentDTO(student);
        return userDTO;
    }

    public static UserDTO buildTeacher(Teacher teacher) {
        UserDTO userDTO = new UserDTO();
        userDTO.role = Role.TEACHER;
        userDTO.teacher = new TeacherDTO(teacher);
        return userDTO;
    }

    public static UserDTO buildManager(Manager manager) {
        UserDTO userDTO = new UserDTO();
        userDTO.role = Role.STUDENT;
        userDTO.manager = new ManagerDTO(manager);
        return userDTO;
    }

    public String getIdentity() {
        if (Role.MANAGER.equals(role)) {
            if (manager != null) {
                return manager.getManagerNumber();
            }
        } else if (Role.TEACHER.equals(role)) {
            if (teacher != null) {
                return teacher.getTeacherNumber();
            }
        } else if (Role.STUDENT.equals(role)) {
            if (student != null) {
                return student.getStudentNumber();
            }
        }
        return null;
    }

    public String getName() {
        if (Role.MANAGER.equals(role)) {
            if (manager != null) {
                return manager.getName();
            }
        } else if (Role.TEACHER.equals(role)) {
            if (teacher != null) {
                return teacher.getName();
            }
        } else if (Role.STUDENT.equals(role)) {
            if (student != null) {
                return student.getName();
            }
        }
        return null;
    }

    public Long getId() {
        if (Role.MANAGER.equals(role)) {
            if (manager != null) {
                return manager.getId();
            }
        } else if (Role.TEACHER.equals(role)) {
            if (teacher != null) {
                return teacher.getId();
            }
        } else if (Role.STUDENT.equals(role)) {
            if (student != null) {
                return student.getId();
            }
        }
        return null;
    }

    public static UserDTO test(String role) {
        UserDTO userDTO = new UserDTO();
        if (Role.MANAGER.equals(role)) {
            userDTO.role = Role.MANAGER;
            return userDTO;
        }
        if (Role.TEACHER.equals(role)) {
            userDTO.role = Role.TEACHER;
            userDTO.teacher = TeacherDTO.test();
            return userDTO;

        }
        userDTO.role = Role.STUDENT;
        userDTO.student = StudentDTO.test();
        return userDTO;
    }

    public boolean isAdmin() {
        if (Role.MANAGER.equals(role)) {
            if (manager != null) {
                return Constant.MGR_NUMBER.equals(manager.getManagerNumber());
            }
        }
        return false;
    }

}
