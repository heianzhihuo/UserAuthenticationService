package com.gtja.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfig
@MapperScan(value = "com.gtja.user.mapper")
public class ProviderApplication {

    public static void main(String[] args){

        SpringApplication.run(ProviderApplication.class,args);
    }
}
