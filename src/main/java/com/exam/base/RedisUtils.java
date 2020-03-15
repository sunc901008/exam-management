package com.exam.base;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSONObject;
import com.exam.domain.dto.uc.UserDTO;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * redis 池和操作
 *
 * @author sunc
 * @date 2020/3/7 14:34
 */

public class RedisUtils {
    private static final Logger logger = Logger.getLogger(RedisUtils.class);
    private static final String ACCESS_TOKEN = "access_token_";
    private static final String USER_PREFIX = "user_";

    private static RedisDS redisDS;

    /**
     * 获取Jedis实例
     */
    private synchronized static Jedis getJedis() {
        if (redisDS == null) {
            redisDS = RedisDS.create(Configs.setting, "");
        }
        return redisDS.getJedis();
    }

    private static String tokenKey(String accessToken) {
        return ACCESS_TOKEN + accessToken;
    }

    private static String userKey(Long userId) {
        return USER_PREFIX + userId;
    }

    public static void delete(String accessToken) {
        if (StrUtil.isBlank(accessToken)) {
            logger.info("accessToken is blank");
            return;
        }
        try (Jedis jedis = getJedis()) {
            String key = tokenKey(accessToken);
            logger.info("UC del redis:" + accessToken);
            String userKey = jedis.get(key);
            if (StrUtil.isNotBlank(userKey)) {
                jedis.del(key);
            }
        }
    }

    public static UserDTO getUserByToken(String accessToken) {
        try (Jedis jedis = getJedis()) {
            String key = tokenKey(accessToken);
            String userKey = jedis.get(key);
            if (StrUtil.isEmpty(userKey)) {
                logger.info("Can't find user with access token:" + accessToken);
                return null;
            } else {
                String userJson = jedis.get(userKey);
                if (StrUtil.isEmpty(userJson)) {
                    logger.info("Can't find user with user key:" + userKey);
                    return null;
                }

                UserDTO userDTO = JSONObject.parseObject(userJson, UserDTO.class);
                jedis.expire(key, Configs.expire);
                jedis.expire(userKey, Configs.expire);

                return userDTO;
            }
        }
    }

    /**
     * 单用户登录过滤
     */
    public static void setUser(String accessToken, UserDTO userDTO) {
        try (Jedis jedis = getJedis()) {
            String userKey = userKey(userDTO.getId());
            if (!Configs.ucMultiLogin) {
                //不允许多登录
                Set<String> keys = jedis.keys(ACCESS_TOKEN);
                for (String key : keys) {
                    String currentUserKey = jedis.get(key);
                    if (userKey.equals(currentUserKey)) {
                        jedis.del(key);
                    }
                }
            }

            String key = tokenKey(accessToken);
            jedis.setex(key, Configs.expire, userKey);
            jedis.setex(userKey, Configs.expire, userDTO.toString());
        }
    }

}
