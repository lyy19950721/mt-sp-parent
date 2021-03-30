package com.mayikt.api.member.dto.req;

import lombok.Data;

/**
 * @Classname UserLoginWxOpenIdReqDto
 * @Description TODO
 * @Date 2021/3/29 11:13
 * @Created by li.yy
 */
@Data
public class UserLoginWxOpenIdReqDto {

    private String wxOpenIdToken;

    private String passWord;

    private String mobile;
}
