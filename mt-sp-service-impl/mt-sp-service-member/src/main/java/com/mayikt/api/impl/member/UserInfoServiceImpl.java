package com.mayikt.api.impl.member;

import com.mayikt.api.base.BaseApiService;
import com.mayikt.api.base.BaseResponse;
import com.mayikt.api.impl.entity.UserInfoDo;
import com.mayikt.api.impl.manage.UserLoginLogManage;
import com.mayikt.api.impl.mapper.UserInfoMapper;
import com.mayikt.api.member.UserInfoService;
import com.mayikt.api.member.dto.resp.UserInfoDto;
import com.mayikt.api.utils.DesensitizationUtil;
import com.mayikt.api.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoServiceImpl extends BaseApiService<UserInfoDto> implements UserInfoService {
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserLoginLogManage userLoginLogManage;

    @Override
    public BaseResponse<UserInfoDto> getUserInfo(String token) {
        if (StringUtils.isEmpty(token)) {
            return setResultError("token is null");
        }
        // 根据token 从redis中获取userid
        String tokenValue = tokenUtils.getTokenValue(token);
        if (StringUtils.isEmpty(tokenValue)) {
            return setResultError("该用户会话已经过期");
        }
        Long userId = Long.parseLong(tokenValue);
        UserInfoDo userInfoDo = userInfoMapper.selectById(userId);
        if (userInfoDo == null) {
            return setResultError("该用户会话已经过期");
        }
        // do 转化dto
        UserInfoDto userInfoDto = doToDto(userInfoDo, UserInfoDto.class);
        userInfoDto.setMobile(DesensitizationUtil.mobileEncrypt(userInfoDto.getMobile()));
        return setResultSuccess(userInfoDto);
    }
}
