package com.an.blog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    // 过期时间设置(12小时)
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    // 私钥设置------随便乱写的
    private static final String TOKEN_SECRET = "ASuKiN7AwIys0ZanZxc";

    /** 创建token */
    public static String creatToken(Integer id, String username) {
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("typ","JWT");
        header.put("alg","HS256");

        return JWT.create()
                .withHeader(header)
                .withClaim("id", id)
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(TOKEN_SECRET));
    }

    /** 校验token */
    public static boolean verifyToken(final String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static Claim getTokenData(String token, String name) {
        return JWT.decode(token).getClaim(name);
    }

}
