package com.exam.service.manage;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.domain.PageParam;
import com.exam.domain.Pager;
import com.exam.domain.dto.manage.SubjectDTO;
import com.exam.domain.dto.manage.SubjectParam;
import com.exam.domain.entity.manage.Subject;
import com.exam.domain.mapper.manage.SubjectMapper;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunc
 * @date 2020/3/7 15:45
 */
@Service
public class SubjectService {

    @Resource
    private SubjectMapper subjectMapper;

    public SubjectDTO insert(SubjectParam subjectParam) {
        Subject subject = new Subject(subjectParam);
        subjectMapper.insert(subject);
        return new SubjectDTO(subject);
    }

    public boolean update(SubjectParam subjectParam) throws LocalException {
        Long subjectId = subjectParam.getId();
        Subject subject = subjectMapper.selectById(subjectId);
        if (subject == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR);
        }
        Subject o = Subject.copy(subject);
        o.setName(subjectParam.getName());
        return subjectMapper.updateById(subject) > 0;
    }

    public Pager<SubjectDTO> list(PageParam pageParam, SubjectParam subjectParam) {
        Page<Subject> page = new Page<>(pageParam.getNumber(), pageParam.getSize());
        LambdaQueryWrapper<Subject> wrapper = Wrappers.lambdaQuery();
        String name = subjectParam.getName();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(Subject::getName, name);
        }
        IPage<Subject> subjects = subjectMapper.selectPage(page, wrapper);
        List<SubjectDTO> list = SubjectDTO.toList(subjects.getRecords());
        return new Pager<>(subjects, list);
    }

    /**
     * 硬删除
     */
    public void deletes(List<Long> subIds) {
        subjectMapper.deleteBatchIds(subIds);
    }

}
