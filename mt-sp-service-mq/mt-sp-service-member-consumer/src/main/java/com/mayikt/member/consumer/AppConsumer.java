package com.mayikt.member.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname AppConsumer
 * @Description TODO
 * @Date 2021/3/25 11:06
 * @Created by li.yy
 */
@EnableFeignClients
@SpringBootApplication
public class AppConsumer {

    public static void main(String[] args) {
        SpringApplication.run(AppConsumer.class, args);
    }
}
