package com.exam.domain.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.domain.entity.manage.Manager;
import org.apache.ibatis.annotations.Select;

/**
 * @author sunc
 * @date 2020/3/7 15:36
 */
public interface ManagerMapper extends BaseMapper<Manager> {

    /**
     * 根据编号查找管理员信息
     *
     * @param mgrNum 管理员编号
     * @return 管理员信息
     */
    @Select("SELECT * FROM sys_manager WHERE manager_number = #{mgrNum}")
    Manager selectByMgrNum(String mgrNum);

}
