package com.exam.config.spring;

import com.exam.controller.webscocket.WsTestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 注册拦截器
 *
 * @author sunc
 * @date 2020/3/7 14:18
 */

@Configuration
@EnableWebSocket
public class FocusWebConfigurer extends WebConfigurer implements WebSocketConfigurer {

    private final LocalHandlerInterceptor localHandlerInterceptor;
    private final WsTestController testController;

    @Autowired
    public FocusWebConfigurer(LocalHandlerInterceptor localHandlerInterceptor, WsTestController testController) {
        this.localHandlerInterceptor = localHandlerInterceptor;
        this.testController = testController;
    }

    /**
     * 配置静态资源, 如 html,js,css
     *
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    /**
     * 注册拦截器
     *
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localHandlerInterceptor).addPathPatterns("/**");
    }

    /**
     * 注册 websocket
     *
     * @param registry registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(testController, "/ws/test1").setAllowedOrigins("*");
    }

}
