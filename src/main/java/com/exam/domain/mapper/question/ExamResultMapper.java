package com.exam.domain.mapper.question;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.domain.entity.question.ExamResult;
import com.exam.domain.vo.ExamResultVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunc
 * @date 2020/3/15 15:00
 */

public interface ExamResultMapper extends BaseMapper<ExamResult> {

    /**
     * 根据学生试卷查找试卷答案
     *
     * @param paperId       试卷
     * @param studentNumber 学生
     * @return 内容信息
     */
    @Select("SELECT * FROM sys_exam_result WHERE paper_id = #{paperId} AND student_number = #{studentNumber}")
    ExamResult selectByPaperIdAndStudent(@Param("paperId") Long paperId, @Param("studentNumber") String studentNumber);

    /**
     * 年级排名
     *
     * @param page    分页
     * @param paperId 试卷
     * @param gradeId 年级
     * @return 内容信息
     */
    @Select("SELECT " +
            "a.class_id AS class_id, " +
            "a.class_name AS class_name, " +
            "a.student_number AS student_number, " +
            "a.student_name AS student_name, " +
            "IFNULL(b.score, 0) AS score " +
            "FROM " +
            "(SELECT " +
            "sys_class.id AS class_id, " +
            "sys_class.name AS class_name, " +
            "sys_student.student_number AS student_number, " +
            "sys_student.name AS student_name " +
            "FROM sys_student, sys_class " +
            "WHERE sys_student.class_id = sys_class.id  AND sys_class.grade_id = #{gradeId}) a " +
            "LEFT JOIN sys_exam_result b " +
            "ON a.student_number = b.student_number AND b.paper_id = #{paperId} ORDER BY score DESC")
    List<ExamResultVO> gradeSort(Page<ExamResultVO> page, @Param("paperId") Long paperId, @Param("gradeId") Long gradeId);

    /**
     * 班级排名
     *
     * @param page    分页
     * @param paperId 试卷
     * @param classId 班级
     * @return 内容信息
     */
    @Select("SELECT " +
            "a.student_number AS student_number, " +
            "a.name AS student_name , " +
            "IFNULL(b.score, 0) AS score " +
            "FROM " +
            "sys_student a " +
            "LEFT JOIN sys_exam_result b " +
            "ON a.student_number = b.student_number AND a.class_id = #{classId} AND b.paper_id = #{paperId} ORDER BY score DESC")
    List<ExamResultVO> classSort(Page<ExamResultVO> page, @Param("paperId") Long paperId, @Param("classId") Long classId);

}
