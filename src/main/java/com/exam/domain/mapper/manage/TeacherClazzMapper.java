package com.exam.domain.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.domain.entity.manage.TeacherClazz;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunc
 * @date 2020/3/14 15:08
 */

public interface TeacherClazzMapper extends BaseMapper<TeacherClazz> {

    /**
     * 根据教师编号查找关联信息
     *
     * @param teacherNumber 教师编号
     * @return 关联信息列表
     */
    @Select("SELECT * FROM sys_teacher_class WHERE teacher_number = #{teacherNumber}")
    List<TeacherClazz> selectByTeacherNumber(String teacherNumber);

}
