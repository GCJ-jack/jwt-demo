package com.itheima.jwt.util;

import org.springframework.util.DigestUtils;

public class PasswordEncoder {

    public static String encode(CharSequence rawPassWord){
        return DigestUtils.md5DigestAsHex(rawPassWord.toString().getBytes());
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
    }

}
