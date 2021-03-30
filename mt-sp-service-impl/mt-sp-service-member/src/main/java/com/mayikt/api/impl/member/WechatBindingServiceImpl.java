package com.mayikt.api.impl.member;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayikt.api.base.BaseApiService;
import com.mayikt.api.base.BaseResponse;
import com.mayikt.api.impl.entity.UserInfoDo;
import com.mayikt.api.impl.mapper.UserInfoMapper;
import com.mayikt.api.member.WechatBindingService;
import com.mayikt.api.member.dto.req.UserLoginWxOpenIdReqDto;
import com.mayikt.api.utils.MD5Util;
import com.mayikt.api.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class WechatBindingServiceImpl extends BaseApiService<String> implements WechatBindingService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private TokenUtils tokenUtils;
    @Override
    public BaseResponse<String> wechatBindUserOpenId(UserLoginWxOpenIdReqDto userLoginWxOpenIdReqDto) {
        // 验证参数
        String mobile = userLoginWxOpenIdReqDto.getMobile();
        if (StringUtils.isEmpty(userLoginWxOpenIdReqDto.getMobile())) {
            return setResultError("mobile 不能为空!");
        }
        String passWord = userLoginWxOpenIdReqDto.getPassWord();
        if (StringUtils.isEmpty(userLoginWxOpenIdReqDto.getPassWord())) {
            return setResultError("passWord 不能为空!");
        }
        // md5加密
        String newPassWord = MD5Util.MD5(passWord);
        QueryWrapper<UserInfoDo> userInfoDoQueryWrapper = new QueryWrapper<>();
        userInfoDoQueryWrapper.eq("MOBILE", mobile);
        userInfoDoQueryWrapper.eq("PASSWORD", newPassWord);
        UserInfoDo userInfoDo = userInfoMapper.selectOne(userInfoDoQueryWrapper);
        if (userInfoDo == null) {
            return setResultError("手机号码或者密码错误");
        }
        // 验证成功则，关联数据库中的openid
        String wxOpenIdToken=userLoginWxOpenIdReqDto.getWxOpenIdToken();
        if(StringUtils.isEmpty(wxOpenIdToken)){
            return setResultError("wxOpenIdToken is null");
        }
        String openId = tokenUtils.getTokenValue(wxOpenIdToken);
        if (StringUtils.isEmpty(openId)){
            return setResultError("openId is null");
        }
        // 修改数据库
        userInfoDo.setWxOpenId(openId);
        int result = userInfoMapper.updateById(userInfoDo);
        if(result<=0){
            return setResultError("关联用户openid失败!");
        }
        return setResultSuccess("关联成功");
    }
}
