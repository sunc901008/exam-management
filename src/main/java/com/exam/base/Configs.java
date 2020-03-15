package com.exam.base;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.setting.Setting;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载配置文件
 *
 * @author sunc
 * @date 2020/3/7 14:38
 */

public class Configs {

    private static final Logger logger = Logger.getLogger(Configs.class);

    private static Properties properties = new Properties();

    /**
     * 用户缓存
     */
    public static String redisHost;
    public static int redisPort;
    public static int redisDb;
    public static int expire;

    public static boolean ucMultiLogin = false;
    public static boolean ucByPass = false;

    /**
     * redis 连接池配置
     */
    public static Setting setting = new Setting("conf/redis.setting", true);

    public static Properties ymlProperties() {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        return yaml.getObject();
    }

    /**
     * 从配置文件初始化各种配置
     */
    public static void initConf() {
        // 日志配置
        PropertyConfigurator.configure(Configs.class.getResourceAsStream("/conf/log4j.properties"));
        // 加载 base.properties
        loadProperties();
        // 获取 base.properties 配置
        initProperties();

        // 修改 redis 连接池配置的host, port, password
        setting.put("host", redisHost);
        setting.put("port", redisPort + "");

    }

    /**
     * 加载 base.properties 配置
     */
    private static void loadProperties() {
        try (InputStream is = Configs.class.getResourceAsStream("/conf/base.properties")) {
            InputStream inputStream = new BufferedInputStream(is);
            properties.load(inputStream);
        } catch (Exception e) {
            logger.error(ExceptionUtil.stacktraceToString(e));
        }
    }

    /**
     * 获取 base.properties 配置
     */
    private static void initProperties() {
        redisHost = properties.getProperty("redisHost", "192.168.0.112");
        redisPort = Integer.parseInt(properties.getProperty("redisPort", "30379"));
        redisDb = Integer.parseInt(properties.getProperty("redisDb", "2"));
        expire = Integer.parseInt(properties.getProperty("expire", "2592000"));

        ucMultiLogin = Boolean.parseBoolean(properties.getProperty("ucMultiLogin", "false"));
        ucByPass = Boolean.parseBoolean(properties.getProperty("ucByPass", "false"));

    }

}
