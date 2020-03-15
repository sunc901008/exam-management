package com.exam.config.mybatisplus;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis plus 配置. 多租户解析, 查询分页插件, 乐观锁设置等
 *
 * @author sunc
 * @date 2020/3/7 13:57
 */

@Configuration
@MapperScan(basePackages = {"com.exam.domain.mapper.*"})
public class MybatisPlusConfig {

    /**
     * 乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 多租户属于 SQL 解析部分，依赖 MP 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        List<ISqlParser> sqlParserList = CollectionUtil.newArrayList();
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

}
