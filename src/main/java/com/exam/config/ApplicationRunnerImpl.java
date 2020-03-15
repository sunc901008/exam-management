package com.exam.config;

import com.exam.base.Constant;
import com.exam.domain.entity.manage.Manager;
import com.exam.domain.mapper.manage.ManagerMapper;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import org.apache.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始化
 *
 * @author sunc
 * @date 2019/11/18 17:34
 */

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    private static final Logger logger = Logger.getLogger(ApplicationRunnerImpl.class);

    @Resource
    private ManagerMapper managerMapper;

    @Override
    public void run(ApplicationArguments args) throws LocalException {
        // 系统启动时，初始化 admin 的管理员
        Manager admin = managerMapper.selectByMgrNum(Constant.MGR_NUMBER);
        if (admin == null) {
            // 未初始化 admin 用户, 则初始化
            boolean insert = managerMapper.insert(Manager.initAdmin()) > 0;
            if (!insert) {
                // 初始化失败
                logger.warn("Init admin fail.");
                throw new LocalException(ExceptionCode.SQL_ERROR);
            }
        }
    }
}
