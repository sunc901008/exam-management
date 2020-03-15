package com.exam.domain.dto.manage;

import com.exam.domain.entity.manage.Grade;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 年级信息返回类
 *
 * @author sunc
 * @date 2020/3/8 12:56
 */

@Getter
public class GradeDTO {
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String name;

    public GradeDTO() {
    }

    public GradeDTO(Grade grade) {
        this.id = grade.getId();
        this.createTime = grade.getCreateTime();
        this.updateTime = grade.getUpdateTime();

        this.name = grade.getName();
    }

    public static List<GradeDTO> toList(List<Grade> grades) {
        return grades.stream().map(GradeDTO::new).collect(Collectors.toList());
    }

    public static GradeDTO getGrade(Long gradeId, List<Grade> grades) {
        for (Grade grade : grades) {
            if (grade.getId().equals(gradeId)) {
                return new GradeDTO(grade);
            }
        }
        return null;
    }
}
