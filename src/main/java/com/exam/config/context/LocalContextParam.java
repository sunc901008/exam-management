package com.exam.config.context;

import com.exam.domain.dto.uc.UserDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 上下文参数
 *
 * @author sunc
 * @date 2020/3/7 13:54
 */

@Getter
@Setter
class LocalContextParam {
    /**
     * 当前用户的 id
     */
    private Long userId;

    /**
     * 当前用户的唯一标识
     */
    private String identity;

    /**
     * 当前用户的名字
     */
    private String name;

    /**
     * 语言环境
     */
    private String language;

    /**
     * 日志记录id
     */
    private Long logId;

    /**
     * 当前ip
     */
    private String currentIp;

    /**
     * 当前用户的accessToken
     */
    private String accessToken;

    /**
     * 角色标识
     */
    private String role;

    /**
     * 角色信息
     */
    private UserDTO user;

}
