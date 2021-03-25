package com.mayikt.api.impl.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.mayikt.api.impl.entity.UserLoginLogDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class LoginProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMsgLoginFollowUp(Long userId, String sourceIp, Date date, String userToken, String channel,
                                     String deviceInfor, String wxOpenId, String phone) {
        /**
         * 1.交换机名称
         * 2.路由key名称
         * 3.发送内容
         */

        JSONObject data = new JSONObject();
        data.put("userId", userId);
        data.put("loginIp", sourceIp);
        data.put("loginTime", date);
        data.put("loginToken", userToken);
        data.put("channel", channel);
        data.put("equipment", deviceInfor);
        data.put("openId", wxOpenId);
        data.put("phone", phone);
        amqpTemplate.convertAndSend("/mayikt_ex", "", data.toJSONString());
        log.info(">>登录之后投递mq消息，异步处理后续操作..<<");
    }

//    private static SerializeConfig mapping = new SerializeConfig();
//
//    public static String toJSON(Object jsonText) {
//        return JSON.toJSONString(jsonText, SerializerFeature.WriteDateUseDateFormat);
//    }
//
//    /**
//     * 自定义时间格式
//     *
//     * @param jsonText
//     * @return
//     */
//    public static String toJSON(String dateFormat, String jsonText) {
//        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
//        return JSON.toJSONString(jsonText, mapping);
//    }
}
