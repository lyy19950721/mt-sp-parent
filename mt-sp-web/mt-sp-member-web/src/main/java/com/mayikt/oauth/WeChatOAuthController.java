package com.mayikt.oauth;

import com.mayikt.api.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/weChat")
@Slf4j
public class WeChatOAuthController {


    @Value("${mayikt.wx.redirectUri}")
    private String redirectUri;
    @Value("${mayikt.vue.addres}")
    private String vueAddres;
    @Autowired
    private TokenUtils tokenUtils;


    @Autowired
    private WxMpService wxMpService;

    /**
     * 生成授权链接
     *http://127.0.0.1:2000/weChat/authorizedConnection
     * @return
     */
    @RequestMapping("/authorizedConnection")
    public String authorizedConnection() {
        String state = UUID.randomUUID().toString();
        String returnRedirectUri =
                wxMpService.oauth2buildAuthorizationUrl(redirectUri, WxConsts.OAuth2Scope.SNSAPI_USERINFO, state);
        log.info(">returnRedirectUri{}<", returnRedirectUri);
        return "redirect:" + returnRedirectUri;
    }


    /**
     * 微信公众号授权授权成功跳转 前端vue
     *
     * @return
     */
    @RequestMapping("/wechatOAuth")
    public String wechatOAuth(String code, String state) {
        log.info(">微信网页开始授权<");
        // 根据用户获取openid
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            String openId = wxMpOAuth2AccessToken.getOpenId();
            log.info(">用户授权获取到openId{}:<", openId);
            // 将用户的 openid存入到Redis中
            String openIdToken = tokenUtils.createToken(openId);
            // 跳转到vue
            return "redirect:"+vueAddres + "?openIdToken=" + openIdToken;
        } catch (Exception e) {
            log.info(">用户授权error{}:<", e);
            return "error";
        }

    }

}
