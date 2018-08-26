package com.webank;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
//@MapperScan(basePackages = "com.webank.dao")
public class WeChatOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeChatOrderApplication.class, args);
    }
}
