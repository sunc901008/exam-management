package com.exam.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 数据库字段值自动填充设置
 *
 * @author sunc
 * @date 2019/11/18 17:45
 */

@Component
public class FocusMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_TIME = "updateTime";
    private static final String VERSION = "version";

    /**
     * insert 操作时自动填充字段值
     *
     * @param metaObject meta
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        if (metaObject.hasSetter(CREATE_TIME)) {
            // 自动填充 create_time 为当前时间
            setInsertFieldValByName(CREATE_TIME, Calendar.getInstance().getTime(), metaObject);
        }

        if (metaObject.hasSetter(UPDATE_TIME)) {
            // 自动填充 update_time 为当前时间
            setInsertFieldValByName(UPDATE_TIME, Calendar.getInstance().getTime(), metaObject);
        }

        if (metaObject.hasSetter(VERSION)) {
            // 乐观锁, 自动填充
            setInsertFieldValByName(VERSION, 1L, metaObject);
        }

    }

    /**
     * update 操作时自动更新 update_time
     *
     * @param metaObject meta
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATE_TIME)) {
            setUpdateFieldValByName(UPDATE_TIME, Calendar.getInstance().getTime(), metaObject);
        }
    }

}