package com.exam.domain.dto;

import com.alibaba.fastjson.JSONObject;
import com.exam.exception.LocalException;

/**
 * @author sunc
 * @date 2020/3/7 16:29
 */
public abstract class BaseDTO {

    public static <T> T init(String text, Class<T> clazz) {
        return JSONObject.parseObject(text, clazz);
    }

    /**
     * 基础参数校验
     *
     * @throws LocalException ExceptionCode
     */
    protected abstract void check() throws LocalException;

}
