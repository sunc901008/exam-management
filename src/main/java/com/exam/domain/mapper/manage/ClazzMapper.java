package com.exam.domain.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.domain.entity.manage.Clazz;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunc
 * @date 2020/3/7 15:36
 */
public interface ClazzMapper extends BaseMapper<Clazz> {

    /**
     * 根据年级编号查找班级信息
     *
     * @param gradeId 年级编号
     * @return 班级信息列表
     */
    @Select("SELECT * FROM sys_class WHERE grade_id = #{gradeId}")
    List<Clazz> selectByGradeId(Long gradeId);

}
