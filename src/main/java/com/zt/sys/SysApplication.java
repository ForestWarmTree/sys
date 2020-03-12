package com.zt.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.zt.sys.authority.mapper"})
@EnableCaching  //@EnableCaching 开启缓存支持
public class SysApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysApplication.class, args);
    }

}
