package com.exam.service.manage;

import com.exam.domain.dto.manage.GradeDTO;
import com.exam.domain.dto.manage.GradeSubjectParam;
import com.exam.domain.dto.manage.SubjectDTO;
import com.exam.domain.entity.manage.Grade;
import com.exam.domain.entity.manage.GradeSubject;
import com.exam.domain.entity.manage.Subject;
import com.exam.domain.mapper.manage.GradeMapper;
import com.exam.domain.mapper.manage.GradeSubjectMapper;
import com.exam.domain.mapper.manage.SubjectMapper;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sunc
 * @date 2020/3/7 15:45
 */
@Service
public class GradeSubjectService {

    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private GradeMapper gradeMapper;
    @Resource
    private GradeSubjectMapper gradeSubjectMapper;

    public List<SubjectDTO> getSubjectByGrade(Long gradeId) throws LocalException {
        Grade grade = gradeMapper.selectById(gradeId);
        if (grade == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR, String.valueOf(gradeId));
        }
        List<GradeSubject> gradeSubjectList = gradeSubjectMapper.selectByGradeId(gradeId);
        List<Long> subjectIds = gradeSubjectList.stream().map(GradeSubject::getSubjectId).collect(Collectors.toList());
        List<Subject> subjects = subjectMapper.selectBatchIds(subjectIds);
        return SubjectDTO.toList(subjects);
    }

    public List<GradeDTO> getClassBySubject(Long subjectId) throws LocalException {
        Subject subject = subjectMapper.selectById(subjectId);
        if (subject == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR, String.valueOf(subjectId));
        }
        List<GradeSubject> gradeSubjectList = gradeSubjectMapper.selectBySubjectId(subjectId);
        List<Long> gradeIds = gradeSubjectList.stream().map(GradeSubject::getGradeId).collect(Collectors.toList());
        List<Grade> grades = gradeMapper.selectBatchIds(gradeIds);
        return GradeDTO.toList(grades);
    }

    public void gradeSetSubject(GradeSubjectParam param) {
        List<Long> gradeIds = param.getGradeIds();
        List<Long> subjectIds = param.getSubjectIds();
        gradeIds.forEach(gradeId -> gradeSetSubject(gradeId, subjectIds));
    }

    private void gradeSetSubject(Long gradeId, List<Long> subjectIds) {
        Grade grade = gradeMapper.selectById(gradeId);
        if (grade != null) {
            subjectIds.forEach(subjectId -> {
                Subject subject = subjectMapper.selectById(subjectId);
                if (subject != null) {
                    gradeSubjectMapper.insert(new GradeSubject(gradeId, subjectId));
                }
            });
        }
    }

    public void gradeDelSubject(GradeSubjectParam param) {
        List<Long> gradeIds = param.getGradeIds();
        List<Long> subjectIds = param.getSubjectIds();
        gradeIds.forEach(gradeId -> gradeDelSubject(gradeId, subjectIds));
    }

    private void gradeDelSubject(Long gradeId, List<Long> subjectIds) {
        Grade grade = gradeMapper.selectById(gradeId);
        if (grade != null) {
            subjectIds.forEach(subjectId -> {
                Subject subject = subjectMapper.selectById(subjectId);
                if (subject != null) {
                    GradeSubject cs = gradeSubjectMapper.selectByParam(gradeId, subjectId);
                    if (cs != null) {
                        gradeSubjectMapper.deleteById(cs.getId());
                    }
                }
            });
        }
    }

}
