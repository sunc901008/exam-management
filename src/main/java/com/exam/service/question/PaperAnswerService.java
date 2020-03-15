package com.exam.service.question;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.config.context.LocalContext;
import com.exam.domain.PageParam;
import com.exam.domain.Pager;
import com.exam.domain.dto.question.*;
import com.exam.domain.entity.manage.Clazz;
import com.exam.domain.entity.manage.Grade;
import com.exam.domain.entity.manage.Subject;
import com.exam.domain.entity.question.ExamResult;
import com.exam.domain.entity.question.Paper;
import com.exam.domain.entity.question.PaperAnswer;
import com.exam.domain.entity.question.PaperQuestion;
import com.exam.domain.mapper.manage.ClazzMapper;
import com.exam.domain.mapper.manage.GradeMapper;
import com.exam.domain.mapper.manage.SubjectMapper;
import com.exam.domain.mapper.question.ExamResultMapper;
import com.exam.domain.mapper.question.PaperAnswerMapper;
import com.exam.domain.mapper.question.PaperMapper;
import com.exam.domain.mapper.question.PaperQuestionMapper;
import com.exam.domain.vo.ExamResultVO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunc
 * @date 2020/3/15 15:04
 */

@Service
public class PaperAnswerService {

    @Resource
    private PaperService paperService;
    @Resource
    private PaperMapper paperMapper;
    @Resource
    private PaperQuestionMapper paperQuestionMapper;
    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private ClazzMapper clazzMapper;
    @Resource
    private GradeMapper gradeMapper;

    @Resource
    private PaperAnswerMapper paperAnswerMapper;
    @Resource
    private ExamResultMapper examResultMapper;
    @Resource
    private ExamResultServiceImpl examResultService;

    /**
     * 获取答卷详情
     */
    public PaperAnswerDTO selectDetail(PaperAnswerParam param) throws LocalException {
        return selectDetail(param.getPaperId(), param.getStudentNumber());
    }

    /**
     * 学生获取答卷详情
     */
    public PaperAnswerDTO selectDetail(Long paperId) throws LocalException {
        return selectDetail(paperId, LocalContext.getIdentity());
    }

    /**
     * 获取答卷详情
     */
    private PaperAnswerDTO selectDetail(Long paperId, String studentNumber) throws LocalException {
        Paper paper = paperMapper.selectById(paperId);
        if (paper == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR);
        }

        List<PaperQuestion> pqs = paperQuestionMapper.selectByPaperId(paperId);
        List<PaperAnswer> paperAnswers = paperAnswerMapper.selectByPaperIdAndStudent(paperId, studentNumber);

        PaperAnswerDTO dto = new PaperAnswerDTO(paper);

        for (int i = 0; i < pqs.size(); i++) {
            PaperQuestion question = pqs.get(i);
            PaperAnswer answer = paperAnswers.get(i);
            dto.addAnswer(question, answer);
        }
        return dto;
    }

    /**
     * 学生获取已发布的试卷详情
     */
    public PaperDTO selectPublishDetail(Long id) throws LocalException {
        return paperService.selectPublishDetail(id);
    }

    /**
     * 学生获取已经发布的试卷列表
     */
    public Pager<PaperDTO> publishList(PageParam pageParam, PaperParam param) {
        return paperService.publishList(pageParam, param);
    }

    /**
     * 学生提交试卷答案
     */
    public void submit(PaperAnswerParam param) {
        List<PaperAnswer> paperAnswers = PaperAnswer.build(param);

        // 试卷所有题目, 有答案的可以即时批改
        List<PaperQuestion> pqs = paperQuestionMapper.selectByPaperId(param.getPaperId());

        for (int i = 0; i < pqs.size(); i++) {
            PaperQuestion question = pqs.get(i);
            PaperAnswer answer = paperAnswers.get(i);
            if (StrUtil.isNotBlank(question.getAnswer())) {
                // 比较答案是否一致(1.数据规范, todo 2.多选题校验)
                if (question.getAnswer().trim().equals(answer.getAnswer().trim())) {
                    answer.setScore(question.getScore());
                } else {
                    answer.setScore(0);
                }
            }
            paperAnswerMapper.insert(answer);
        }
    }

    /**
     * 试卷某题给分
     */
    public void score(AnswerScoreParam param) {
        Long id = param.getId();
        Integer score = param.getScore();

        PaperAnswer answer = paperAnswerMapper.selectById(id);
        if (answer != null) {
            PaperAnswer o = PaperAnswer.copy(answer);
            o.setScore(score);
            paperAnswerMapper.updateById(o);
        }

    }

    /**
     * 统计某学生的成绩
     */
    public Integer countScore(PaperScoreParam param) {
        Long paperId = param.getPaperId();
        String studentNumber = param.getStudentNumber();

        Integer totalScore = paperAnswerMapper.countScore(paperId, studentNumber);

        ExamResult er = new ExamResult(studentNumber, paperId, totalScore);
        examResultMapper.insert(er);
        return totalScore;
    }

    public Pager<ExamResultVO> gradeSort(PageParam pageParam, Long paperId, Long gradeId) {
        Paper paper = paperMapper.selectById(paperId);
        Subject subject = subjectMapper.selectById(paper.getSubjectId());
        Grade grade = gradeMapper.selectById(gradeId);
        Page<ExamResultVO> page = new Page<>(pageParam.getNumber(), pageParam.getSize());
        IPage<ExamResultVO> results = examResultService.gradeSort(page, paperId, gradeId);
        List<ExamResultVO> examResults = results.getRecords();
        ExamResultVO.update(examResults, paper, subject.getName(), grade);
        return new Pager<>(results, examResults);
    }

    public Pager<ExamResultVO> classSort(PageParam pageParam, Long paperId, Long classId) {
        Paper paper = paperMapper.selectById(paperId);
        Subject subject = subjectMapper.selectById(paper.getSubjectId());
        Clazz clazz = clazzMapper.selectById(classId);
        Grade grade = gradeMapper.selectById(clazz.getGradeId());

        Page<ExamResultVO> page = new Page<>(pageParam.getNumber(), pageParam.getSize());
        IPage<ExamResultVO> results = examResultService.classSort(page, paperId, classId);
        List<ExamResultVO> examResults = results.getRecords();
        ExamResultVO.update(examResults, paper, subject.getName(), clazz, grade);
        return new Pager<>(results, examResults);
    }

}
