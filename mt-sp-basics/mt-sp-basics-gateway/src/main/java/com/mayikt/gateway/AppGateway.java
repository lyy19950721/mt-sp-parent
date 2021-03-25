package com.mayikt.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppGateway {
    public static void main(String[] args) {
        //Gateway 底层基于 webflux编写的  需要去除 springboot-web组件
        SpringApplication.run(AppGateway.class);
    }
}
