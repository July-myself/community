package com.july.community.controller;

import com.july.community.dto.QuestionDTO;
import com.july.community.model.Question;
import com.july.community.model.User;
import com.july.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Long id,
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
            question.setId(id);
            //进行插入或更新的操作
            questionService.createOrUpdate(question);
            return "redirect:/"; //跳转回首页
        } else {
            //未获取到用户信息，提示未登录
            model.addAttribute("error","用户未登录");
            return "publish";
        }
    }

    @GetMapping("publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO question = questionService.getListById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }
}
