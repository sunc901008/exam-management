package com.exam.domain.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.domain.entity.manage.GradeSubject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunc
 * @date 2020/3/7 15:36
 */
public interface GradeSubjectMapper extends BaseMapper<GradeSubject> {

    /**
     * 根据班级编号查找班级学科信息
     *
     * @param gradeId 班级编号
     * @return 班级学科关联信息列表
     */
    @Select("SELECT * FROM sys_grade_subject WHERE grade_id = #{gradeId}")
    List<GradeSubject> selectByGradeId(Long gradeId);

    /**
     * 根据学科编号查找班级学科信息
     *
     * @param subjectId 学科编号
     * @return 班级学科关联信息列表
     */
    @Select("SELECT * FROM sys_grade_subject WHERE subject_id = #{subjectId}")
    List<GradeSubject> selectBySubjectId(Long subjectId);

    /**
     * 根据学科编号查找班级学科信息
     *
     * @param gradeId   班级编号
     * @param subjectId 学科编号
     * @return 班级学科关联信息列表
     */
    @Select("SELECT * FROM sys_grade_subject WHERE grade_id = #{gradeId} AND subject_id = #{subjectId}")
    GradeSubject selectByParam(@Param("gradeId") Long gradeId, @Param("subjectId") Long subjectId);
}
