package com.exam.service.question;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.domain.entity.question.ExamResult;
import com.exam.domain.mapper.question.ExamResultMapper;
import com.exam.domain.vo.ExamResultVO;
import org.springframework.stereotype.Service;

/**
 * @author sunc
 * @date 2020/3/15 16:34
 */
@Service
public class ExamResultServiceImpl extends ServiceImpl<ExamResultMapper, ExamResult> implements ExamResultService {

    @Override
    public IPage<ExamResultVO> gradeSort(Page<ExamResultVO> page, Long paperId, Long gradeId) {
        return page.setRecords(this.baseMapper.gradeSort(page, paperId, gradeId));
    }

    @Override
    public IPage<ExamResultVO> classSort(Page<ExamResultVO> page, Long paperId, Long classId) {
        return page.setRecords(this.baseMapper.classSort(page, paperId, classId));
    }

}
