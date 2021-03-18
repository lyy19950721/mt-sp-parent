package com.mayikt.api.impl.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Classname WeChatServiceFeign
 * @Description TODO
 * @Date 2021/3/10 16:37
 * @Created by li.yy
 */
@FeignClient("mayikt-weixin")
public interface WeChatServiceFeign {

    @GetMapping("/getWeChat")
    String getWeChat(@RequestParam("a") Integer a);
}
