package com.exam.service.manage;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.policy.PasswordPolicy;
import com.exam.domain.PageParam;
import com.exam.domain.Pager;
import com.exam.domain.dto.manage.StudentDTO;
import com.exam.domain.dto.manage.StudentParam;
import com.exam.domain.entity.manage.Student;
import com.exam.domain.mapper.manage.StudentMapper;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunc
 * @date 2020/3/7 15:45
 */
@Service
public class StudentService {

    @Resource
    private StudentMapper studentMapper;

    public void inserts(List<Student> students) {
        students.forEach(studentMapper::insert);
    }

    public StudentDTO insert(StudentParam studentParam) {
        Student student = new Student(studentParam);
        studentMapper.insert(student);
        return new StudentDTO(student);
    }

    public boolean update(StudentParam studentParam) throws LocalException {
        Long id = studentParam.getId();
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR);
        }
        Student o = Student.copy(student);
        o.setName(studentParam.getName());
        String password = studentParam.getPassword();
        if (StrUtil.isNotBlank(password)) {
            o.setPassword(PasswordPolicy.encrypt(password));
        }
        Long classId = studentParam.getClassId();
        if (Validator.isNotNull(classId)) {
            o.setClassId(classId);
        }
        return studentMapper.updateById(o) > 0;
    }

    public Pager<StudentDTO> list(PageParam pageParam, StudentParam studentParam) {
        Page<Student> page = new Page<>(pageParam.getNumber(), pageParam.getSize());
        LambdaQueryWrapper<Student> wrapper = Wrappers.lambdaQuery();
        String name = studentParam.getName();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(Student::getName, name);
        }
        IPage<Student> students = studentMapper.selectPage(page, wrapper);
        List<StudentDTO> list = StudentDTO.toList(students.getRecords());
        return new Pager<>(students, list);

    }

    /**
     * 软删除
     */
    public void deletes(List<Long> stdIds) {
        stdIds.forEach(id -> {
            Student student = studentMapper.selectById(id);
            if (student != null) {
                Student o = Student.copy(student);
                o.setDeleted(true);
                studentMapper.updateById(o);
            }
        });
    }

}
