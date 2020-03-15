package com.exam.domain.dto.manage;

import com.exam.domain.entity.manage.Manager;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生信息返回类
 *
 * @author sunc
 * @date 2020/3/7 13:45
 */
@Getter
public class ManagerDTO {
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String managerNumber;
    private String name;

    public ManagerDTO() {
    }

    public ManagerDTO(Manager manager) {
        this.id = manager.getId();
        this.createTime = manager.getCreateTime();
        this.updateTime = manager.getUpdateTime();

        this.name = manager.getName();
        this.managerNumber = manager.getManagerNumber();
    }

    public static List<ManagerDTO> toList(List<Manager> managers) {
        return managers.stream().map(ManagerDTO::new).collect(Collectors.toList());
    }

    public static ManagerDTO test() {
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.id = 9999L;
        managerDTO.name = "test";
        return managerDTO;
    }

}
