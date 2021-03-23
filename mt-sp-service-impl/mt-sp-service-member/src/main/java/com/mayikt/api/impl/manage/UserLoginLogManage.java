package com.mayikt.api.impl.manage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayikt.api.impl.entity.UserLoginLogDo;
import com.mayikt.api.impl.mapper.UserLoginLogMapper;
import com.mayikt.api.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserLoginLogManage {
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Async("taskExecutor")
    public void asynLoginLog(UserLoginLogDo userLoginLogDo) {
        // 1.uniqueLogin()唯一登录
        uniqueLogin(userLoginLogDo);
        // 2.插入db记录数据
        insert(userLoginLogDo);
        // 3.调用微信服务接口发送消息
        // 4.其他耗时操作代码
    }
    private void uniqueLogin(UserLoginLogDo userLoginLogDo){
        // 根据userid+渠道 限制唯一登录
        QueryWrapper<UserLoginLogDo> userLoginLogDoQueryWrapper = new QueryWrapper<>();
        userLoginLogDoQueryWrapper.eq("user_id",userLoginLogDo.getUserId());
        userLoginLogDoQueryWrapper.eq("channel",userLoginLogDo.getChannel());
        UserLoginLogDo dbUserLoginLogDo = userLoginLogMapper.selectOne(userLoginLogDoQueryWrapper);
        if(dbUserLoginLogDo==null){
            return;
        }
        // 逻辑删除
        int result = userLoginLogMapper.deleteById(dbUserLoginLogDo.getId());
        if(result>0){
            // 清除之前的token
            redisUtil.delKey(dbUserLoginLogDo.getLoginToken());
        }
    }
    private void insert(UserLoginLogDo userLoginLogDo){
        int insert = userLoginLogMapper.insert(userLoginLogDo);
        log.info(">>>db中插入日志记录<<<");
    }
}
