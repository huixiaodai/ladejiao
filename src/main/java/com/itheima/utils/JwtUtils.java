package com.itheima.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    //写一个自己知道的签名密钥
    private static final String SECRET = "huixiaodai";

    //过期时间：3小时
    private static final long EXPIRE_TIME = 60*60*1000*3;

    public static String generateToken(Integer id,String username,String updateTime) {
        return JWT.create()
                .withClaim("id",id)
                .withClaim("username",username)
                .withClaim("updateTime",updateTime)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }

    //解析token,验证token
    public static Map<String,Object> parseToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);
        Map<String,Object> map = new HashMap<>();
        map.put("id",decodedJWT.getClaim("id").asInt());
        map.put("username",decodedJWT.getClaim("username").asString());
        map.put("updateTime",decodedJWT.getClaim("updateTime").asString());

        return map;
    }
}
