package com.mayikt.member.consumer.feign;

import com.mayikt.api.weixin.WechatTemplateService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("mayikt-weixin")
public interface WechatTemplateServiceFeign extends WechatTemplateService {
}
