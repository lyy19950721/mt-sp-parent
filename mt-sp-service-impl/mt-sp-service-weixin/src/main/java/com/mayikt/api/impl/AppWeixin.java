package com.mayikt.api.impl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname AppWeixin
 * @Description TODO
 * @Date 2021/3/10 16:11
 * @Created by li.yy
 */
@SpringBootApplication
@MapperScan("com.mayikt.api.impl.mapper")
public class AppWeixin {

    public static void main(String[] args) {
        SpringApplication.run(AppWeixin.class, args);
    }
}
