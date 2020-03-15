package com.exam.domain.entity.manage;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.base.Constant;
import com.exam.base.policy.PasswordPolicy;
import com.exam.domain.dto.manage.ManagerParam;
import com.exam.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sunc
 * @date 2020/3/7 13:39
 */
@Getter
@Setter
@TableName("sys_manager")
public class Manager extends BaseEntity {
    /**
     * 编号
     */
    @TableField("manager_number")
    private String managerNumber;
    private String name;
    private String password;

    public Manager() {
    }

    public Manager(ManagerParam param) {
        this.setId(param.getId());
        this.name = param.getName();
        this.managerNumber = param.getManagerNumber();
        this.password = PasswordPolicy.encrypt(param.getPassword());
    }

    public static Manager initAdmin() {
        Manager manager = new Manager();
        manager.setId(Constant.MGR_ID);
        manager.managerNumber = Constant.MGR_NUMBER;
        manager.name = Constant.MGR_NAME;
        manager.password = PasswordPolicy.encrypt(Constant.MGR_PASSWORD);
        return manager;
    }

    public static Manager copy(Manager manager) {
        Manager entity = new Manager();
        entity.setId(manager.getId());
        entity.setVersion(manager.getVersion());
        return entity;
    }

}
