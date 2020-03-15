package com.exam.service.manage;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.policy.PasswordPolicy;
import com.exam.domain.PageParam;
import com.exam.domain.Pager;
import com.exam.domain.dto.manage.TeacherDTO;
import com.exam.domain.dto.manage.TeacherParam;
import com.exam.domain.entity.manage.*;
import com.exam.domain.mapper.manage.*;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sunc
 * @date 2020/3/7 15:45
 */
@Service
public class TeacherService {

    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private TeacherClazzMapper teacherClazzMapper;
    @Resource
    private ClazzMapper clazzMapper;
    @Resource
    private GradeMapper gradeMapper;
    @Resource
    private SubjectMapper subjectMapper;

    public void inserts(List<Teacher> teachers) {
        teachers.forEach(teacherMapper::insert);
    }

    public TeacherDTO insert(TeacherParam teacherParam) {
        Teacher teacher = new Teacher(teacherParam);
        teacherMapper.insert(teacher);
        return new TeacherDTO(teacher);
    }

    public boolean update(TeacherParam teacherParam) throws LocalException {
        Long id = teacherParam.getId();
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR);
        }
        Teacher o = Teacher.copy(teacher);
        o.setName(teacherParam.getName());
        String password = teacherParam.getPassword();
        if (StrUtil.isNotBlank(password)) {
            o.setPassword(PasswordPolicy.encrypt(password));
        }
        return teacherMapper.updateById(o) > 0;
    }

    public Pager<TeacherDTO> list(PageParam pageParam, TeacherParam teacherParam) {
        Page<Teacher> page = new Page<>(pageParam.getNumber(), pageParam.getSize());
        LambdaQueryWrapper<Teacher> wrapper = Wrappers.lambdaQuery();
        String name = teacherParam.getName();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(Teacher::getName, name);
        }
        IPage<Teacher> teachers = teacherMapper.selectPage(page, wrapper);
        List<Teacher> res = teachers.getRecords();
        List<Subject> subjects = subjectMapper.selectBatchIds(res.stream().map(Teacher::getSubjectId).collect(Collectors.toList()));
        List<TeacherDTO> list = TeacherDTO.toList(res, subjects);
        return new Pager<>(teachers, list);

    }

    /**
     * 软删除
     */
    public void deletes(List<Long> tchIds) {
        tchIds.forEach(id -> {
            Teacher teacher = teacherMapper.selectById(id);
            if (teacher != null) {
                Teacher o = Teacher.copy(teacher);
                o.setDeleted(true);
                teacherMapper.updateById(o);
            }
        });
    }

    /**
     * 根据教师编号获取教师所教的班级
     */
    public List<Clazz> getClazzByTeacherId(String teacherNumber) {
        List<TeacherClazz> tc = teacherClazzMapper.selectByTeacherNumber(teacherNumber);
        List<Long> clsIds = tc.stream().map(TeacherClazz::getClassId).collect(Collectors.toList());
        return clazzMapper.selectBatchIds(clsIds);
    }

    /**
     * 根据教师编号获取教师所教的班级所在年级
     */
    public List<Grade> getGradeByTeacherId(String teacherNumber) {
        List<Clazz> clazzList = getClazzByTeacherId(teacherNumber);
        List<Long> gradeIds = clazzList.stream().map(Clazz::getGradeId).collect(Collectors.toList());
        return gradeMapper.selectBatchIds(gradeIds);
    }

}
