package com.itheima.jwt.controller;

import com.itheima.jwt.entity.JwtUser;
import com.itheima.jwt.util.PasswordEncoder;
import com.itheima.jwt.util.TokenProvider;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
public class TokenController {

    private final static HashMap<String, String> USER = new HashMap<>() {
        {
            put("admin", "e10adc3949ba59abbe56e057f20f883e");
        }
    };


    @GetMapping("/login")
    public String login(String username, String password){
        if(PasswordEncoder.matches(password, USER.get(username))){
            return TokenProvider.createToken("1", "web", "admin");
        }
        return  "error";
    }

    @GetMapping("/token/validate")
    public JwtUser tokenValidate(String token){
        return TokenProvider.checkToken(token);
    }

}
