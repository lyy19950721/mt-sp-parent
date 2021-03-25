package com.mayikt.member.consumer;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.api.base.BaseResponse;
import com.mayikt.api.weixin.dto.req.LoginReminderReqDto;
import com.mayikt.member.consumer.feign.WechatTemplateServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "fanout_wechattemplate_queue")
public class WechatTemplateConsumer {
    @Autowired
    private WechatTemplateServiceFeign wechatTemplateServiceFeign;

    @RabbitHandler
    public void process(String msg) {
        try {
            log.info(">>调用微信模板接口发送微信模板提醒:{}<<", msg);
            LoginReminderReqDto loginReminderReqDto = JSONObject.parseObject(msg, LoginReminderReqDto.class);

            BaseResponse<String> stringBaseResponse = wechatTemplateServiceFeign.sendTemplate(loginReminderReqDto);
            log.info(">>调用微信模板接口响应参数:stringBaseResponse{}<<", stringBaseResponse);
        } catch (Exception e) {
            log.error("调用微信模板接口异步处理信息异常，{}", e);
        }
    }


}
