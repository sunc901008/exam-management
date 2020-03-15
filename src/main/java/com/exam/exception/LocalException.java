package com.exam.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * 自定义异常基类
 *
 * @author sunc
 * @date 2020/3/7 14:10
 */

public class LocalException extends Exception {
    static final int DEFAULT_ERROR = 10000;
    public String message;
    public int errCode;
    public JSONObject msgParams;

    public LocalException() {
        this(DEFAULT_ERROR);
    }

    public LocalException(int errCode) {
        this(errCode, null);
    }

    public LocalException(int errCode, String msg) {
        this(errCode, msg, null);
    }

    public LocalException(int errCode, String msg, JSONObject msgParams) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
        this.msgParams = msgParams;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("errCode", this.errCode);
        json.put("message", this.message);
        json.put("msgParams", this.msgParams);
        return json.toJSONString();
    }

}
