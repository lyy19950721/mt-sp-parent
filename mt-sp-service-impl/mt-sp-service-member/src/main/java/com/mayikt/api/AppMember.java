package com.mayikt.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname AppMember
 * @Description TODO
 * @Date 2021/3/10 16:40
 * @Created by li.yy
 */
@EnableFeignClients
@SpringBootApplication
@MapperScan("com.mayikt.api.impl.mapper")
public class AppMember {

    public static void main(String[] args) {
        SpringApplication.run(AppMember.class, args);
    }
}
