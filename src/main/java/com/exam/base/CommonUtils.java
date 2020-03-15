package com.exam.base;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.exam.base.Constant;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 公共方法
 *
 * @author sunc
 * @date 2020/3/7 14:13
 */

public class CommonUtils {
    private static final Logger logger = Logger.getLogger(CommonUtils.class);

    /**
     * 对象转json字符串
     *
     * @param obj      object
     * @param withNull 是否包含 null 值
     * @return json string
     */
    public static String toJsonString(Object obj, boolean withNull) {
        if (obj == null) {
            return "{}";
        }
        if (withNull) {
            return JSON.toJSONString(obj, Constant.JSON_FEATURE_WITH_NULL);
        }
        return JSON.toJSONString(obj, Constant.JSON_FEATURE_WITHOUT_NULL);
    }

    /**
     * 获取随机 uuid 字符串, 去除 "-"
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取ip地址
     *
     * @param request HttpServletRequest
     */
    public static String getIpAddress(HttpServletRequest request) {
        String unKnown = "unknown";
        String xip = request.getHeader("X-Real-IP");
        String xFor = request.getHeader("X-Forwarded-For");
        if (StrUtil.isNotEmpty(xFor) && !unKnown.equalsIgnoreCase(xFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if (index != -1) {
                xFor = xFor.substring(0, index);
                return xFor;
            } else {
                return xFor;
            }
        }
        xFor = xip;
        if (StrUtil.isNotEmpty(xFor) && !"unKnown".equalsIgnoreCase(xFor)) {
            return xFor;
        }
        if (StrUtil.isBlank(xFor) || unKnown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isBlank(xFor) || unKnown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isBlank(xFor) || unKnown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StrUtil.isBlank(xFor) || unKnown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StrUtil.isBlank(xFor) || unKnown.equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }
        return xFor;
    }

    public static String getLanguage(HttpServletRequest request) {
        String language = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if ("focusdata.lang".equals(cookie.getName())) {
                language = cookie.getValue();
                break;
            }
        }
        return language;
    }

    public static String readFile(InputStream is) {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        try (BufferedInputStream bufferedInput = new BufferedInputStream(is)) {
            int bytesRead;
            while ((bytesRead = bufferedInput.read(buffer)) != -1) {
                String chunk = new String(buffer, 0, bytesRead);
                sb.append(chunk);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
