package com.zyf.zojbackendcommon.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JWTUtils {
    private static final long JWT_TTL = 24 * 60 * 60 * 1000;

    public static String createToken(Long userId, String userRole, String secret){

        // Header
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        //Payload
        Map<String,Object> claims = new HashMap<>();

        //自定义数据，根据业务需要添加
        claims.put("userId", userId);
        claims.put("userRole", userRole);

        //标准中注册的声明
        claims.put("iss", "zyf");

        //生成 Token
        return Jwts.builder()
                .setHeader(map)         // 添加Header信息
                .setClaims(claims)      // 添加Payload信息
                .setId(UUID.randomUUID().toString()) // 设置jti：是JWT的唯一标识
                .setIssuedAt(new Date())       // 设置iat: jwt的签发时间
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TTL)) // 设置exp：jwt过期时间，3600秒=1小时
                .setSubject("Jack")    //设置sub：代表这个jwt所面向的用户
                .signWith(SignatureAlgorithm.HS256, secret)//设置签名：通过签名算法和秘钥生成签名
                .compact();
    }

    /**
     * 从 Token 中获取 Payload 信息
     */
    public static Claims getClaimsFromToken(String token, String secret) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

}
