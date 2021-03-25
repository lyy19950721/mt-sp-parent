package com.mayikt.api.member;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.api.base.BaseApiService;
import com.mayikt.api.base.BaseResponse;
import com.mayikt.api.member.dto.req.UserLoginDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface JWTLoginService {
    /**
     * jwt登录的方式
     *
     * @return
     */
    @PostMapping("loginJwt")
    BaseResponse<JSONObject> loginJwt(@RequestBody UserLoginDto userLoginDto);

    /**
     * jwt 验证
     *
     * @return
     */
    @GetMapping("jwtVerification")
    BaseResponse<JSONObject> jwtVerification(@RequestParam("jwt") String jwt);
}
