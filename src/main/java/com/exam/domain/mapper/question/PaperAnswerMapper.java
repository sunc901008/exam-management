package com.exam.domain.mapper.question;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.domain.entity.question.PaperAnswer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunc
 * @date 2020/3/15 15:00
 */

public interface PaperAnswerMapper extends BaseMapper<PaperAnswer> {

    /**
     * 根据学生试卷查找试卷答案
     *
     * @param paperId       试卷
     * @param studentNumber 学生
     * @return 内容信息
     */
    @Select("SELECT * FROM sys_paper_answer WHERE paper_id = #{paperId} AND student_number = #{studentNumber} ORDER BY index")
    List<PaperAnswer> selectByPaperIdAndStudent(@Param("paperId") Long paperId, @Param("studentNumber") String studentNumber);

    /**
     * 统计学生试卷总分
     *
     * @param paperId       试卷
     * @param studentNumber 学生
     * @return 内容信息
     */
    @Select("SELECT SUM(score) FROM sys_paper_answer WHERE paper_id = #{paperId} AND student_number = #{studentNumber}")
    Integer countScore(@Param("paperId") Long paperId, @Param("studentNumber") String studentNumber);

}
