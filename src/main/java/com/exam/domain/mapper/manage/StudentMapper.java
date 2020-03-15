package com.exam.domain.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.domain.entity.manage.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunc
 * @date 2020/3/7 15:36
 */
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 根据学号查找学生信息
     *
     * @param studentNumber 学号
     * @return 学生信息
     */
    @Select("SELECT * FROM sys_student WHERE student_number = #{studentNumber}")
    Student selectByStdNum(String studentNumber);

    /**
     * 根据班级编号查找学生信息
     *
     * @param classId 班级编号
     * @return 学生信息列表
     */
    @Select("SELECT * FROM sys_student WHERE class_id = #{classId}")
    List<Student> selectByClassId(Long classId);

}
