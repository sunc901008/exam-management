package com.exam.service.manage;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.Constant;
import com.exam.base.policy.PasswordPolicy;
import com.exam.domain.dto.manage.ManagerDTO;
import com.exam.domain.dto.manage.ManagerParam;
import com.exam.domain.PageParam;
import com.exam.domain.Pager;
import com.exam.domain.entity.manage.Manager;
import com.exam.domain.mapper.manage.ManagerMapper;
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
public class ManagerService {

    @Resource
    private ManagerMapper managerMapper;

    public ManagerDTO insert(ManagerParam managerParam) {
        Manager manager = new Manager(managerParam);
        managerMapper.insert(manager);
        return new ManagerDTO(manager);
    }

    public boolean update(ManagerParam managerParam) throws LocalException {
        Long id = managerParam.getId();
        Manager manager = managerMapper.selectById(id);
        if (manager == null) {
            throw new LocalException(ExceptionCode.OBJECT_NULL_ERROR);
        }
        Manager o = Manager.copy(manager);
        o.setName(managerParam.getName());
        String password = managerParam.getPassword();
        if (StrUtil.isNotBlank(password)) {
            o.setPassword(PasswordPolicy.encrypt(password));
        }
        return managerMapper.updateById(manager) > 0;
    }

    public Pager<ManagerDTO> list(PageParam pageParam, ManagerParam managerParam) {
        Page<Manager> page = new Page<>(pageParam.getNumber(), pageParam.getSize());
        LambdaQueryWrapper<Manager> wrapper = Wrappers.lambdaQuery();
        String name = managerParam.getName();
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(Manager::getName, name);
        }
        IPage<Manager> managers = managerMapper.selectPage(page, wrapper);
        List<ManagerDTO> list = ManagerDTO.toList(managers.getRecords());
        return new Pager<>(managers, list);
    }

    /**
     * 硬删除
     */
    public void deletes(List<Long> mgrIds) {
        // admin 用户禁止删除
        mgrIds.remove(Constant.MGR_ID);
        managerMapper.deleteBatchIds(mgrIds);
    }

}
