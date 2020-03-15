package com.exam.domain.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.domain.entity.manage.Teacher;
import org.apache.ibatis.annotations.Select;

/**
 * @author sunc
 * @date 2020/3/7 15:36
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    /**
     * 根据编号查找教师信息
     *
     * @param tchNum 教师编号
     * @return 教师信息
     */
    @Select("SELECT * FROM sys_teacher WHERE teacher_number = #{tchNum}")
    Teacher selectByTchNum(String tchNum);

}
