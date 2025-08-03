package com.itheima.jwt.util;

import com.itheima.jwt.entity.JwtUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;

public class AuthStorage {

    public static final String TOKEN_KEY = "token";

    private static final HashMap<String, JwtUser> JWT_USER = new HashMap<String,JwtUser>();

    public static JwtUser getUser(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return JWT_USER.get(request.getHeader(TOKEN_KEY));
    }

    public static void setUser(String token, JwtUser user) {
        JWT_USER.put(token, user);
    }

    public static void clearUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        JWT_USER.remove(request.getHeader(TOKEN_KEY));
    }

}
