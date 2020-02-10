package com.july.community.controller;

import com.july.community.mapper.PublishMapper;
import com.july.community.model.Question;
import com.july.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private PublishMapper publishMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ){
        //存下填写的内容，若发布失败，下次仍然写入页面
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        //从session中获取user问题的创建者
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.getId() != null){
            Question question = new Question();
            //为问题对象属性赋值
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getId());
            question.setTimeCreate(System.currentTimeMillis());
            question.setTimeModified(System.currentTimeMillis());
            publishMapper.create(question);//插入数据库
            return "redirect:/"; //跳转回首页
        } else {
            //未获取到用户信息，提示未登录
            model.addAttribute("error","用户未登录");
            return "publish";
        }
    }
}
