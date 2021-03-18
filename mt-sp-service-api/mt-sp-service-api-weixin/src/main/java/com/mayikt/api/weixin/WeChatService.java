package com.mayikt.api.weixin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Classname WeChatService
 * @Description TODO
 * @Date 2021/3/10 16:03
 * @Created by li.yy
 */
public interface WeChatService {

    @GetMapping("/getWeChat")
    String getWeChat(@RequestParam("a") Integer a);
}
