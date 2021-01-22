package com.atguigu.mp.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 指定mapper接口存放的包，包名必须指定的是mapper接口指定的包
@MapperScan(value = "com.atguigu.mp.mapper")
public class MybatisConfig {
    // 配置mybatis 的 分页拦截器  如果使用分页了，该拦截器会自动拦截sql进行修改
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }


    // 配置乐观锁的拦截器 实现更新版本号 的更新以及 版本号条件的添加
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

}
