package com.july.community.controller;

import com.july.community.mapper.UserMapper;
import com.july.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token); //去数据库寻找该token值的用户信息
                    if(user != null){ //若找到了这个用户信息
                        //写进session，让页面去展示
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }

            }
        }

        return "index";
    }
}
