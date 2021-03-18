package com.mayikt.api.member;

import com.mayikt.api.base.BaseResponse;
import com.mayikt.api.member.dto.req.UserReqDto;
import com.mayikt.api.member.dto.resp.UserRespDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Classname MemberService
 * @Description TODO
 * @Date 2021/3/10 16:26
 * @Created by li.yy
 */
public interface MemberService {

    @GetMapping("memberToWeiXin")
    String memberToWeiXin(Integer a);

    /**
     * 不符合规范
     * @return
     */
    @GetMapping("addMember")
    BaseResponse addMember(String userName, String pwd, Integer age);

    @PostMapping("updateUser")
    Object updateUser(@RequestBody Map<String, String> map);

    @PostMapping("updateUserDto")
    BaseResponse<UserRespDto> updateUserDto(@RequestBody UserReqDto userReqDto);

    @RequestMapping("/getTestConfig")
    String getTestConfig();
}
