package com.mayikt.api.member;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.api.base.BaseResponse;
import com.mayikt.api.member.dto.req.UserLoginWxOpenIdReqDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface WechatBindingService {

    /**
     * 绑定用户openid
     * @return
     */
    @PostMapping("/wechatBindUserOpenId")
    BaseResponse<String> wechatBindUserOpenId(
         @RequestBody UserLoginWxOpenIdReqDto userLoginWxOpenIdReqDto);
}
