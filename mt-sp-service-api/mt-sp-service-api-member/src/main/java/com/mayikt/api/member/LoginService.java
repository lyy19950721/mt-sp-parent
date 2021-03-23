package com.mayikt.api.member;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.api.base.BaseResponse;
import com.mayikt.api.member.dto.req.UserLoginDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
public interface LoginService {
    /**
     * 提供登录接口
     * @param userLoginDto
     * @return
     */
    @PostMapping("/login")
    BaseResponse<JSONObject> login(@RequestBody UserLoginDto userLoginDto);
}
