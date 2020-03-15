package com.exam.domain.dto.manage;

import com.exam.domain.entity.manage.Subject;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 科目信息返回类
 *
 * @author sunc
 * @date 2020/3/8 12:56
 */

@Getter
public class SubjectDTO {
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String name;

    public SubjectDTO() {
    }

    public SubjectDTO(Subject subject) {
        this.id = subject.getId();
        this.createTime = subject.getCreateTime();
        this.updateTime = subject.getUpdateTime();

        this.name = subject.getName();
    }

    public static List<SubjectDTO> toList(List<Subject> subjects) {
        return subjects.stream().map(SubjectDTO::new).collect(Collectors.toList());
    }

    public static SubjectDTO getSubject(Long subjectId, List<Subject> subjects) {
        for (Subject subject : subjects) {
            if (subject.getId().equals(subjectId)) {
                return new SubjectDTO(subject);
            }
        }
        return null;
    }

}
