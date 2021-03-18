package com.mayikt.api.impl.weixin;

import com.mayikt.api.weixin.WeChatService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname WeChatServiceImpl
 * @Description TODO
 * @Date 2021/3/10 16:05
 * @Created by li.yy
 */
@RestController
public class WeChatServiceImpl implements WeChatService {

    @Override
    public String getWeChat(Integer a) {
        return "每特教育微信服务a:"+a;
    }
}
