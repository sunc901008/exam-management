package com.exam;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.exam.base.Configs;
import com.exam.base.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.List;
import java.util.Properties;

/**
 * 项目启动类
 *
 * @author sunc
 * @date 2020/3/7 14:47
 */

@SpringBootApplication
public class Start {

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        //1.需要定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2:添加fastJson的配置信息
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(Constant.JSON_FEATURE_WITH_NULL);
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = CollectionUtil.newArrayList();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters((HttpMessageConverter<?>) fastJsonHttpMessageConverter);
    }

    public static void main(String[] args) {
        // 初始化配置文件
        Configs.initConf();
        // 初始化 springboot yml 配置
        Properties defaultProperties = Configs.ymlProperties();
        SpringApplication app = new SpringApplication(Start.class);
        app.setDefaultProperties(defaultProperties);
        app.run(args);
    }

}