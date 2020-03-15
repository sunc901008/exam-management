package com.exam.domain.mapper.question;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.domain.entity.question.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunc
 * @date 2020/3/14 12:41
 */

public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 获取所有类型的题目
     *
     * @param subjectId 科目
     * @param gradeId   年级
     * @param type      类型
     * @return 关联信息列表
     */
    @Select("SELECT id FROM sys_question WHERE subject_id = #{subjectId} AND " +
            "grade_id = #{gradeId} AND " +
            "type = #{type}")
    List<Question> allSubjectGradeType(@Param("subjectId") Long subjectId, @Param("gradeId") Long gradeId, @Param("type") Integer type);

}
