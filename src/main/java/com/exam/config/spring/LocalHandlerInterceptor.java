package com.exam.config.spring;

import cn.hutool.core.util.StrUtil;
import com.exam.base.*;
import com.exam.base.privilege.Role;
import com.exam.config.context.LocalContext;
import com.exam.domain.dto.uc.UserDTO;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * rest 接口拦截器. 记录请求开始日志和结束, 获取多租户标识
 *
 * @author sunc
 * @date 2020/3/7 13:59
 */

@Component
public class LocalHandlerInterceptor implements HandlerInterceptor {
    private static final Logger logger = Logger.getLogger(LocalHandlerInterceptor.class);

    private static final List<String> PASS_PATH = Arrays.asList("/uc/login", "/uc/pwd");
    private static final String MGR_REQUEST = "/manager/";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws LocalException {
        String flag = UUID.randomUUID().toString();
        Long start = System.currentTimeMillis();
        request.setAttribute("flag", flag);
        request.setAttribute("start", start);
        String uri = request.getRequestURI();
        String requestMethod = request.getMethod();
        // for test
        String role = request.getParameter("role");
        logger.info(String.format("Flag:%s. Received request. Path:%s. Method:%s.", flag, uri, requestMethod));

        // 过滤不需要登录的请求
        if (passCheck(uri)) {
            return true;
        }

        String accessToken = request.getHeader(Constant.HEADER_ACCESS_TOKEN);
        UserDTO user = getUser(accessToken, role);
        if (uri.startsWith(MGR_REQUEST) && !user.isAdmin()) {
            throw new LocalException(ExceptionCode.NO_PERMISSION_ERROR, "uri:" + uri);
        }
        request.setAttribute("user", user);
        String currentIp = CommonUtils.getIpAddress(request);
        LocalContext.init(accessToken, CommonUtils.getLanguage(request), currentIp, user);

        // 权限检测
        if (Role.MANAGER.equals(user.getRole())) {
            return true;
        } else if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RoleCheck roleCheck = method.getAnnotation(RoleCheck.class);
            if (roleCheck != null) {
                // 方法注解中需要的权限
                String[] privileges = roleCheck.value();
                if (Arrays.asList(privileges).contains(user.getRole())) {
                    return true;
                }
                throw new LocalException(ExceptionCode.NO_PERMISSION_ERROR);
            }
            return true;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            logger.error(ex.getMessage());
        }
        LocalContext.remove();
        String flag = request.getAttribute("flag").toString();
        Long start = (Long) request.getAttribute("start");
        Long cost = System.currentTimeMillis() - start;
        logger.info(String.format("Flag:%s. Response request. Cost: %s", flag, cost));
    }

    private UserDTO getUser(String accessToken, String role) throws LocalException {
        if (Configs.ucByPass && StrUtil.isNotBlank(role)) {
            return UserDTO.test(role);
        }
        UserDTO userDTO = RedisUtils.getUserByToken(accessToken);
        if (userDTO == null) {
            throw new LocalException(ExceptionCode.NOT_LOGIN_ERROR);
        }
        return userDTO;
    }

    private boolean passCheck(String uri) {
        return PASS_PATH.contains(uri);
    }

}
