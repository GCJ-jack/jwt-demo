package com.itheima.jwt.util;

import com.itheima.jwt.entity.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class TokenProvider {

    private static final String SALT_KEY = "links";

    private static final long TOKEN_VALIDITY = 86400000;

    private static String SECRET_KEY = Base64.getEncoder().encodeToString(SALT_KEY.getBytes(StandardCharsets.UTF_8));

    public static String createToken(String userId, String clientId, String role){
        Date validity = new Date((new Date().getTime() + TOKEN_VALIDITY));

        return Jwts.builder()
                //代表着 jwt这个主体 它的所有人
                .setSubject(String.valueOf(userId))
                //代表这个签发的主体
                .setIssuer("")
                //是一个时间戳
                .setIssuedAt(new Date())
                .setAudience(clientId)
                // 代表这个JWT的接收对象
                .claim("role", role)
                .claim("userId",userId)
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .setExpiration(validity)
                .compact();
    }

    public static JwtUser checkToken(String token){
        if(validateToken(token)){
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            String audience = claims.getAudience();
            String userId = claims.get("userId", String.class);
            String role = claims.get("role", String.class);
            JwtUser jwtUser = new JwtUser().setUserId(userId).setRole(role).setValid(true);
            log.info("===token有效{},客户端{}", jwtUser, audience);
            return jwtUser;
        }
        log.error("***token无效***");
        return new JwtUser();
    }


    private static boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        }catch (Exception e){
            log.error("无效的token: " + authToken);
        }
        return false;
    }
}
