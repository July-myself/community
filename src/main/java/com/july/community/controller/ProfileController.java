package com.july.community.controller;

import com.july.community.dto.PaginationDTO;
import com.july.community.model.User;
import com.july.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size ,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        //获取页签名
            model.addAttribute("section",action);
            if ("questions".equals(action)){
                model.addAttribute("sectionName","我的提问");
            }
            if("replies".equals(action)){
                model.addAttribute("sectionName","最新回复");
            }

            PaginationDTO paginationDTO = questionService.getList(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);

        return "profile";
    }
}
