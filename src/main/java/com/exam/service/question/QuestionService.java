package com.exam.service.question;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.level.LevelCount;
import com.exam.base.level.LevelDesign;
import com.exam.config.context.LocalContext;
import com.exam.domain.PageParam;
import com.exam.domain.Pager;
import com.exam.domain.dto.manage.TeacherDTO;
import com.exam.domain.dto.question.QuestionDTO;
import com.exam.domain.dto.question.QuestionParam;
import com.exam.domain.dto.question.QuestionScoreParam;
import com.exam.domain.dto.uc.UserDTO;
import com.exam.domain.entity.BaseEntity;
import com.exam.domain.entity.manage.Grade;
import com.exam.domain.entity.manage.Subject;
import com.exam.domain.entity.question.Question;
import com.exam.domain.mapper.manage.GradeMapper;
import com.exam.domain.mapper.manage.SubjectMapper;
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
public class QuestionService {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private TeacherService teacherService;

    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private GradeMapper gradeMapper;

    public QuestionDTO insert(QuestionParam param) {
        Question question = new Question(param);
        questionMapper.insert(question);
        return new QuestionDTO(question);
    }

    public QuestionDTO detail(Long id) throws LocalException {
        boolean withAnswer = !LocalContext.isStudent();
        Question question = questionMapper.selectById(id);
        if (question == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR);
        }
        Subject subject = subjectMapper.selectById(question.getSubjectId());
        Grade grade = gradeMapper.selectById(question.getGradeId());
        return new QuestionDTO(question, subject, grade, withAnswer);
    }

    public Pager<QuestionDTO> list(PageParam pageParam, QuestionParam param) {
        Page<Question> page = new Page<>(pageParam.getNumber(), pageParam.getSize());
        LambdaQueryWrapper<Question> wrapper = Wrappers.lambdaQuery();
        Long gradeId = param.getGradeId();
        if (gradeId != null && gradeId > 0) {
            wrapper.eq(Question::getGradeId, gradeId);
        }
        if (LocalContext.isTeacher()) {
            // 老师只能看到自己所在年级所教科目的试题库
            UserDTO user = LocalContext.getUser();
            if (user != null) {
                TeacherDTO teacher = user.getTeacher();
                List<Grade> grades = teacherService.getGradeByTeacherId(teacher.getTeacherNumber());
                wrapper.in(Question::getGradeId, grades.stream().map(BaseEntity::getId).collect(Collectors.toList()));
            }
        }
        IPage<Question> questions = questionMapper.selectPage(page, wrapper);
        List<Question> res = questions.getRecords();
        List<Subject> subjects = subjectMapper.selectBatchIds(res.stream().map(Question::getSubjectId).collect(Collectors.toList()));
        List<Grade> grades = gradeMapper.selectList(null);
        List<QuestionDTO> list = QuestionDTO.toList(res, subjects, grades);
        return new Pager<>(questions, list);
    }

    /**
     * 赢删除
     */
    public void deletes(List<Long> qIds) {
        questionMapper.deleteBatchIds(qIds);
    }

    public boolean update(QuestionParam param) throws LocalException {
        Long id = param.getId();
        Question question = questionMapper.selectById(id);
        if (question == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR);
        }
        Question o = Question.copy(question);
        if (Validator.isNotNull(param.getLevel())) {
            o.setLevel(param.getLevel());
        }
        o.setSubjectId(param.getSubjectId());
        if (StrUtil.isNotBlank(param.getType())) {
            o.setType(param.getType());
        }
        if (StrUtil.isNotBlank(param.getTip())) {
            o.setTip(param.getTip());
        }
        if (StrUtil.isNotBlank(param.getChapter())) {
            o.setChapter(param.getChapter());
        }
        o.setGradeId(param.getGradeId());
        if (StrUtil.isNotBlank(param.getContent())) {
            o.setContent(param.getContent());
        }
        if (StrUtil.isNotBlank(param.getAnswer())) {
            o.setAnswer(param.getAnswer());
        }
        return questionMapper.updateById(o) > 0;
    }

    List<Question> random(LevelDesign levelDesign, QuestionScoreParam param, Long subjectId, Long gradeId) {
        List<Question> questions = CollectionUtil.newArrayList();
        Integer type = param.getType();
        List<Question> allQuestions = questionMapper.allSubjectGradeType(subjectId, gradeId, type);
        List<LevelCount> levelCounts = levelDesign.getCount(param.getCount());
        levelCounts.sort(Comparator.comparing(LevelCount::getLevel));
        for (LevelCount lc : levelCounts) {
            int count = lc.getCount();
            if (count == 0) {
                continue;
            }
            List<Question> currentLevel = allQuestions.stream().filter(q -> q.getLevel().equals(lc.getLevel())).collect(Collectors.toList());
            if (count >= currentLevel.size()) {
                questions.addAll(currentLevel);
            } else {
                questions.addAll(random(currentLevel, count));
            }
        }
        return questions;

    }

    private List<Question> random(List<Question> questions, int count) {
        List<Integer> indexList = CollectionUtil.newArrayList();
        while (indexList.size() < count) {
            int index = new Double(Math.random() * questions.size()).intValue();
            if (!indexList.contains(index)) {
                indexList.add(index);
            }
        }
        indexList.sort(Integer::compareTo);
        List<Question> res = CollectionUtil.newArrayList();
        indexList.forEach(i -> res.add(questions.get(i)));
        return res;
    }

}
