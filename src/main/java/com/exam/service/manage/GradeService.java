package com.exam.service.manage;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.domain.PageParam;
import com.exam.domain.Pager;
import com.exam.domain.dto.manage.GradeDTO;
import com.exam.domain.dto.manage.GradeParam;
import com.exam.domain.entity.manage.Clazz;
import com.exam.domain.entity.manage.Grade;
import com.exam.domain.mapper.manage.ClazzMapper;
import com.exam.domain.mapper.manage.GradeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunc
 * @date 2020/3/7 15:45
 */
@Service
public class GradeService {

    @Resource
    private GradeMapper gradeMapper;
    @Resource
    private ClazzMapper clazzMapper;

    public GradeDTO insert(GradeParam gradeParam) {
        Grade grade = new Grade(gradeParam);
        gradeMapper.insert(grade);
        return new GradeDTO(grade);
    }

    public Pager<GradeDTO> list(PageParam pageParam) {
        Page<Grade> page = new Page<>(pageParam.getNumber(), pageParam.getSize());
        IPage<Grade> grades = gradeMapper.selectPage(page, null);
        List<GradeDTO> list = GradeDTO.toList(grades.getRecords());
        return new Pager<>(grades, list);
    }

    /**
     * 硬删除, 返回含有class而无法删除的grade名字列表
     */
    public JSONArray deletes(List<Long> gradeIds) {
        JSONArray names = new JSONArray();
        for (Long id : gradeIds) {
            Grade grade = gradeMapper.selectById(id);
            if (grade != null) {
                List<Clazz> clazzList = clazzMapper.selectByGradeId(grade.getId());
                if (clazzList.isEmpty()) {
                    gradeMapper.deleteById(id);
                } else {
                    names.add(grade.getName());
                }
            }
        }
        return names;
    }

}
