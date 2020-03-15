package com.exam.config.context;

import com.exam.base.Constant;
import com.exam.base.privilege.Role;
import com.exam.domain.dto.uc.UserDTO;
import org.springframework.stereotype.Component;

/**
 * 上下文
 *
 * @author sunc
 * @date 2020/3/7 13:56
 */

@Component
public class LocalContext {

    /**
     * ThreadLocal 并发
     */
    private static ThreadLocal<LocalContextParam> context = new ThreadLocal<>();

    public static void init(String accessToken, String ip, String language, UserDTO userDTO) {
        LocalContextParam param = new LocalContextParam();
        param.setAccessToken(accessToken);
        param.setCurrentIp(ip);
        param.setLanguage(language);
        param.setName(userDTO.getName());
        param.setIdentity(userDTO.getIdentity());
        param.setRole(userDTO.getRole());
        param.setUserId(userDTO.getId());
        param.setUser(userDTO);
        context.set(param);
    }

    public static UserDTO getUser() {
        LocalContextParam param = context.get();
        if (param == null) {
            return null;
        }
        return param.getUser();
    }

    public static Long getUserId() {
        LocalContextParam param = context.get();
        if (param == null) {
            return null;
        }
        return param.getUserId();
    }

    public static String getRole() {
        LocalContextParam param = context.get();
        if (param == null) {
            return null;
        }
        return param.getRole();
    }

    public static String getAccessToken() {
        LocalContextParam param = context.get();
        if (param == null) {
            return null;
        }
        return param.getAccessToken();
    }

    public static String getIdentity() {
        LocalContextParam param = context.get();
        if (param == null) {
            return null;
        }
        return param.getIdentity();
    }

    public static boolean isTeacher() {
        return Role.TEACHER.equals(getRole());
    }

    public static boolean isStudent() {
        return Role.STUDENT.equals(getRole());
    }

    public static boolean isAdmin() {
        LocalContextParam param = context.get();
        if (param == null) {
            return false;
        }
        return Role.MANAGER.equals(param.getRole()) && Constant.MGR_NUMBER.equals(param.getIdentity());
    }

    public static void remove() {
        context.remove();
    }

}
