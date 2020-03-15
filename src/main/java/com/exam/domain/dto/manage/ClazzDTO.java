package com.exam.domain.dto.manage;

import com.exam.domain.entity.manage.Clazz;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 班级信息返回类
 *
 * @author sunc
 * @date 2020/3/8 12:56
 */

@Getter
public class ClazzDTO {
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String name;

    public ClazzDTO() {
    }

    public ClazzDTO(Clazz clazz) {
        this.id = clazz.getId();
        this.createTime = clazz.getCreateTime();
        this.updateTime = clazz.getUpdateTime();

        this.name = clazz.getName();
    }

    public static List<ClazzDTO> toList(List<Clazz> clazzList) {
        return clazzList.stream().map(ClazzDTO::new).collect(Collectors.toList());
    }

}
