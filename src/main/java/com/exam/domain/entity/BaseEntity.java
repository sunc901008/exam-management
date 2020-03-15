package com.exam.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author sunc
 * @date 2020/3/7 13:41
 */
@Setter
@Getter
public class BaseEntity {
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @Version
    @TableField(value = "`version`", fill = FieldFill.INSERT)
    private Long version;
}
