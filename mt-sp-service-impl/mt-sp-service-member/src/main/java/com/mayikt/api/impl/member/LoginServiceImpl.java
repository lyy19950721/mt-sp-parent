package com.mayikt.api.impl.member;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayikt.api.base.BaseApiService;
import com.mayikt.api.base.BaseResponse;
import com.mayikt.api.impl.entity.UserDo;
import com.mayikt.api.impl.entity.UserInfoDo;
import com.mayikt.api.impl.entity.UserLoginLogDo;
import com.mayikt.api.impl.manage.UserLoginLogManage;
import com.mayikt.api.impl.mapper.UserInfoMapper;
import com.mayikt.api.member.LoginService;
import com.mayikt.api.member.dto.req.UserLoginDto;
import com.mayikt.api.utils.MD5Util;
import com.mayikt.api.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@Slf4j
public class LoginServiceImpl extends BaseApiService<JSONObject> implements LoginService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserLoginLogManage userLoginLogManage;
    @Override
    public BaseResponse<JSONObject> login(UserLoginDto userLoginDto) {
        // 验证参数
        String mobile = userLoginDto.getMobile();
        if (StringUtils.isEmpty(userLoginDto.getMobile())) {
            return setResultError("mobile 不能为空!");
        }
        String passWord = userLoginDto.getPassWord();
        if (StringUtils.isEmpty(userLoginDto.getPassWord())) {
            return setResultError("passWord 不能为空!");
        }
        // 从请求头中获取 其他信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String deviceInfor = request.getHeader("deviceInfor");
        String channel = request.getHeader("channel");
        String sourceIp = request.getHeader("sourceIp");
        // 设备信息
        if (StringUtils.isEmpty(deviceInfor)) {
            return setResultError("设备信息不能为空!");
        }
        // 渠道来源
        if (StringUtils.isEmpty(channel)) {
            return setResultError("渠道不能为空");
        }
        String oldPassWord = userLoginDto.getPassWord();
        // 对密码实现加密 （考虑密码加盐的问题）
        String newPassWord = MD5Util.MD5(oldPassWord);
        // 登录 dto转化成do
        UserDo userDo = dtoToDo(userLoginDto, UserDo.class);
        //查询数据库db
        QueryWrapper<UserInfoDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MOBILE", mobile);
        queryWrapper.eq("PASSWORD", newPassWord);
        UserInfoDo userInfoDo = userInfoMapper.selectOne(queryWrapper);
        if (userInfoDo == null) {
            return setResultError("手机号码或者密码错误");
        }
        Long userId = userInfoDo.getUserId();
        // 生成用户令牌
        String userToken = tokenUtils.createToken(userId + "");
        JSONObject data = new JSONObject();
        data.put("token", userToken);
        log.info(">>>登录成功:userToken{}<<<",userToken);

        userLoginLogManage.asynLoginLog(new UserLoginLogDo(userId,sourceIp,new Date(),userToken,
                channel,deviceInfor));
        return setResultSuccess(data);
    }
}
