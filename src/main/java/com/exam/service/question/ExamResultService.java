package com.exam.service.question;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.domain.entity.question.ExamResult;
import com.exam.domain.vo.ExamResultVO;

/**
 * @author sunc
 * @date 2020/3/15 16:34
 */
public interface ExamResultService extends IService<ExamResult> {

    /**
     * 年级排名
     *
     * @param page    分页
     * @param paperId 试卷
     * @param gradeId 年级
     * @return 内容信息
     */
    IPage<ExamResultVO> gradeSort(Page<ExamResultVO> page, Long paperId, Long gradeId);

    /**
     * 年级排名
     *
     * @param page    分页
     * @param paperId 试卷
     * @param classId 班级
     * @return 内容信息
     */
    IPage<ExamResultVO> classSort(Page<ExamResultVO> page, Long paperId, Long classId);

}
