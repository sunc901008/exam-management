package com.exam.domain.mapper.question;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.domain.entity.question.PaperQuestion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunc
 * @date 2020/3/14 16:00
 */

public interface PaperQuestionMapper extends BaseMapper<PaperQuestion> {

    /**
     * 根据试卷查找试卷内容
     *
     * @param paperId 试卷
     * @return 内容信息
     */
    @Select("SELECT * FROM sys_paper_question WHERE paper_id = #{paperId} ORDER BY index")
    List<PaperQuestion> selectByPaperId(Long paperId);

    /**
     * 根据试卷查找试卷特定序号的题目
     *
     * @param paperId 试卷
     * @param index   试题序号
     * @return 内容信息
     */
    @Select("SELECT * FROM sys_paper_question WHERE paper_id = #{paperId} AND index = #{index}")
    PaperQuestion selectByIndex(@Param("paperId") Long paperId, @Param("index") Integer index);

}
