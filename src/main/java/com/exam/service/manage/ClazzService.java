package com.exam.service.manage;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.domain.PageParam;
import com.exam.domain.Pager;
import com.exam.domain.dto.manage.ClazzDTO;
import com.exam.domain.dto.manage.ClazzParam;
import com.exam.domain.entity.manage.Clazz;
import com.exam.domain.entity.manage.Student;
import com.exam.domain.mapper.manage.ClazzMapper;
import com.exam.domain.mapper.manage.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunc
 * @date 2020/3/7 15:45
 */
@Service
public class ClazzService {

    @Resource
    private ClazzMapper clazzMapper;
    @Resource
    private StudentMapper studentMapper;

    public ClazzDTO insert(ClazzParam clazzParam) {
        Clazz clazz = new Clazz(clazzParam);
        clazzMapper.insert(clazz);
        return new ClazzDTO(clazz);
    }

    public Pager<ClazzDTO> list(PageParam pageParam, ClazzParam clazzParam) {
        Page<Clazz> page = new Page<>(pageParam.getNumber(), pageParam.getSize());
        LambdaQueryWrapper<Clazz> wrapper = Wrappers.lambdaQuery();
        Long gradeId = clazzParam.getGradeId();
        if (gradeId != null && gradeId > 0) {
            wrapper.eq(Clazz::getGradeId, gradeId);
        }
        IPage<Clazz> clazzList = clazzMapper.selectPage(page, wrapper);
        List<ClazzDTO> list = ClazzDTO.toList(clazzList.getRecords());
        return new Pager<>(clazzList, list);
    }

    /**
     * 硬删除, 返回含有student而无法删除的class名字列表
     */
    public JSONArray deletes(List<Long> clsIds) {
        JSONArray names = new JSONArray();
        for (Long id : clsIds) {
            Clazz clazz = clazzMapper.selectById(id);
            if (clazz != null) {
                List<Student> students = studentMapper.selectByClassId(clazz.getId());
                if (students.isEmpty()) {
                    clazzMapper.deleteById(id);
                } else {
                    names.add(clazz.getName());
                }
            }
        }
        return names;
    }

}
