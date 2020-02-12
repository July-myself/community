package com.july.community.controller;

import com.july.community.dto.PaginationDTO;
import com.july.community.dto.QuestionDTO;
import com.july.community.mapper.UserMapper;
import com.july.community.model.Question;
import com.july.community.model.User;
import com.july.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page, //分页参数：第几页
                        @RequestParam(name = "size",defaultValue = "2") Integer size //分页参数：每页多少条数据
    ){
        //检验登录状态
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

        //显示问题列表
        PaginationDTO pagination = questionService.getList(page,size);
        model.addAttribute("pagination",pagination);


        return "index";
    }
}
