package com.exam.service.question;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.level.LevelDesign;
import com.exam.config.context.LocalContext;
import com.exam.domain.PageParam;
import com.exam.domain.Pager;
import com.exam.domain.dto.manage.TeacherDTO;
import com.exam.domain.dto.question.*;
import com.exam.domain.dto.uc.UserDTO;
import com.exam.domain.entity.BaseEntity;
import com.exam.domain.entity.manage.Grade;
import com.exam.domain.entity.manage.Subject;
import com.exam.domain.entity.question.Paper;
import com.exam.domain.entity.question.PaperQuestion;
import com.exam.domain.entity.question.Question;
import com.exam.domain.mapper.manage.GradeMapper;
import com.exam.domain.mapper.manage.SubjectMapper;
import com.exam.domain.mapper.question.PaperMapper;
import com.exam.domain.mapper.question.PaperQuestionMapper;
import com.exam.domain.mapper.question.QuestionMapper;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import com.exam.service.manage.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sunc
 * @date 2020/3/14 12:41
 */

@Service
public class PaperService {

    @Resource
    private PaperMapper paperMapper;
    @Resource
    private PaperQuestionMapper paperQuestionMapper;
    @Resource
    private TeacherService teacherService;

    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private GradeMapper gradeMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionService questionService;

    /**
     * 获取试卷详情
     */
    public PaperDTO selectDetail(Long id) throws LocalException {
        Paper paper = paperMapper.selectById(id);
        if (paper == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR, "id: " + id);
        }
        Grade grade = gradeMapper.selectById(paper.getGradeId());
        Subject subject = subjectMapper.selectById(paper.getSubjectId());
        List<PaperQuestion> pqs = paperQuestionMapper.selectByPaperId(id);
        boolean withAnswer = !LocalContext.isStudent();
        List<PaperQuestionDTO> questions = pqs.stream().map(p -> PaperQuestionDTO.build(p, withAnswer)).collect(Collectors.toList());
        return new PaperDTO(paper, subject, grade, questions);
    }

    /**
     * 学生获取已发布的试卷详情
     */
    PaperDTO selectPublishDetail(Long id) throws LocalException {
        Paper paper = paperMapper.selectById(id);
        if (paper == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR, "id: " + id);
        }
        if (!paper.getPublished()) {
            throw new LocalException(ExceptionCode.PAPER_NOT_PUBLISH_ERROR, "paper: " + paper.getName());
        }
        Grade grade = gradeMapper.selectById(paper.getGradeId());
        Subject subject = subjectMapper.selectById(paper.getSubjectId());
        List<PaperQuestion> pqs = paperQuestionMapper.selectByPaperId(id);
        List<PaperQuestionDTO> questions = pqs.stream().map(p -> PaperQuestionDTO.build(p, false)).collect(Collectors.toList());
        return new PaperDTO(paper, subject, grade, questions);
    }

    public void insert(Paper paper, List<PaperQuestion> questions) {
        paperMapper.insert(paper);
        Long paperId = paper.getId();
        questions.forEach(q -> {
            q.setPaperId(paperId);
            paperQuestionMapper.insert(q);
        });
    }

    /**
     * 新增一份试卷
     */
    public void add(PaperParam param) throws LocalException {
        Paper paper = new Paper(param);
        List<PaperQuestion> questions = CollectionUtil.newArrayList();

        for (PaperParam.PaperQuestionParam q : param.getQuestions()) {
            Long questionId = q.getQuestionId();
            Question question = questionMapper.selectById(questionId);
            if (question == null) {
                throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR, "question:" + q.getIndex());
            }
            questions.add(PaperQuestion.build(q, question));
        }
        insert(paper, questions);
    }

    /**
     * 自动获取一份试卷
     */
    public PaperDTO auto(AutoPaperParam param) {
        Integer level = param.getLevel();
        Long subjectId = param.getSubjectId();
        Long gradeId = param.getGradeId();

        LevelDesign levelDesign = LevelDesign.paperLevel(level);

        Grade grade = gradeMapper.selectById(gradeId);
        Subject subject = subjectMapper.selectById(subjectId);
        List<QuestionScoreParam> params = param.getParams();
        params.sort(Comparator.comparing(QuestionScoreParam::getType));

        List<PaperQuestionDTO> questions = CollectionUtil.newArrayList();
        int index = 1;
        for (QuestionScoreParam p : params) {
            List<Question> questionList = questionService.random(levelDesign, p, subjectId, gradeId);
            for (Question q : questionList) {
                questions.add(PaperQuestionDTO.build(index++, q, p.getScore()));
            }
        }
        return new PaperDTO(levelDesign.getLevel(), subject, grade, questions);
    }

    /**
     * 学生获取已经发布的试卷列表
     */
    Pager<PaperDTO> publishList(PageParam pageParam, PaperParam param) {
        Page<Paper> page = new Page<>(pageParam.getNumber(), pageParam.getSize());
        LambdaQueryWrapper<Paper> wrapper = Wrappers.lambdaQuery();

        wrapper.eq(Paper::getPublished, true);

        if (StrUtil.isNotBlank(param.getName())) {
            wrapper.like(Paper::getName, param.getName());
        }

        if (param.getSubjectId() != null && param.getSubjectId() > 0) {
            wrapper.eq(Paper::getSubjectId, param.getSubjectId());
        }

        IPage<Paper> papers = paperMapper.selectPage(page, wrapper);
        return new Pager<>(papers, PaperDTO.toList(papers.getRecords()));
    }

    public Pager<PaperDTO> list(PageParam pageParam, PaperParam param) {
        Page<Paper> page = new Page<>(pageParam.getNumber(), pageParam.getSize());
        LambdaQueryWrapper<Paper> wrapper = Wrappers.lambdaQuery();

        if (param.getLevel() != null && param.getLevel() > 0) {
            wrapper.eq(Paper::getLevel, param.getLevel());
        }

        if (StrUtil.isNotBlank(param.getName())) {
            wrapper.like(Paper::getName, param.getName());
        }

        if (param.getSubjectId() != null && param.getSubjectId() > 0) {
            wrapper.eq(Paper::getSubjectId, param.getSubjectId());
        }
        if (LocalContext.isTeacher()) {
            // 老师只能看到自己所在年级所教科目的试卷库
            UserDTO user = LocalContext.getUser();
            if (user != null) {
                TeacherDTO teacher = user.getTeacher();
                List<Grade> grades = teacherService.getGradeByTeacherId(teacher.getTeacherNumber());
                wrapper.in(Paper::getGradeId, grades.stream().map(BaseEntity::getId).collect(Collectors.toList()));
            }
        }

        IPage<Paper> papers = paperMapper.selectPage(page, wrapper);

        List<Paper> res = papers.getRecords();

        List<Subject> subjects = subjectMapper.selectBatchIds(res.stream().map(Paper::getSubjectId).collect(Collectors.toList()));
        List<Grade> grades = gradeMapper.selectList(null);

        List<PaperDTO> list = PaperDTO.toList(papers.getRecords(), subjects, grades);
        return new Pager<>(papers, list);
    }

    /**
     * 软删除(已经生成的试卷里面可能包含试题)
     */
    public void deletes(List<Long> qIds) {
        for (Long id : qIds) {
            Paper paper = paperMapper.selectById(id);
            if (paper != null) {
                Paper o = Paper.copy(paper);
                o.setDeleted(true);
                paperMapper.updateById(o);
            }
        }
    }

    public boolean replace(PaperReplaceParam param) throws LocalException {
        Long id = param.getId();
        Paper paper = paperMapper.selectById(id);
        if (paper == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR, "id:" + id);
        }
        PaperQuestion pq = paperQuestionMapper.selectByIndex(id, param.getIndex());
        if (pq == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR, "index:" + param.getIndex());
        }
        Long questionId = param.getQuestionId();
        Question question = questionMapper.selectById(questionId);
        if (question == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR, "questionId:" + questionId);
        }
        PaperQuestion o = PaperQuestion.copy(pq);
        o.setContent(question.getContent());
        o.setAnswer(question.getAnswer());
        o.setScore(param.getScore());
        return paperQuestionMapper.updateById(o) > 0;
    }

    public boolean update(PaperParam param) throws LocalException {
        Long id = param.getId();
        Paper paper = paperMapper.selectById(id);
        if (paper == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR);
        }
        Paper o = Paper.copy(paper);
        if (Validator.isNotNull(param.getLevel())) {
            o.setLevel(param.getLevel());
        }
        o.setSubjectId(param.getSubjectId());
        if (StrUtil.isNotBlank(param.getName())) {
            o.setName(paper.getName());
        }
        if (Validator.isNotNull(param.getGradeId())) {
            o.setGradeId(param.getGradeId());
        }
        return paperMapper.updateById(o) > 0;
    }

    /**
     * 发布试卷详情
     */
    public boolean publish(Long id) throws LocalException {
        Paper paper = paperMapper.selectById(id);
        if (paper == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR);
        }
        Paper o = Paper.copy(paper);
        o.setPublished(true);
        return paperMapper.updateById(o) > 0;
    }
}
