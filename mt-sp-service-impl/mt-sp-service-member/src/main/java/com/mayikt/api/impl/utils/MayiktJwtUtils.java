package com.mayikt.api.impl.utils;

import com.mayikt.api.impl.entity.UserInfoDo;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * @Classname MayiktJwtUtils
 * @Description TODO
 * @Date 2021/3/25 17:05
 * @Created by li.yy
 */
public class MayiktJwtUtils {

    private static final String SUBJECT = "mayikt";
    private static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;
    private static final String APPSECRET_KEY = "mayikt_secret";

    public static String generateJsonWebToken(UserInfoDo userInfoDo) {
        String compact = Jwts
                .builder()
                .setSubject(SUBJECT)
                .claim("userId", userInfoDo.getUserId())
                .claim("age", userInfoDo.getAge())
                .claim("mobile", userInfoDo.getMobile())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
        return compact;
    }

    public static Claims checkJWT(String jwt) {
        try {
            final Claims body = Jwts
                    .parser()
                    .setSigningKey(APPSECRET_KEY)
                    .parseClaimsJws(jwt)
                    .getBody();
            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
