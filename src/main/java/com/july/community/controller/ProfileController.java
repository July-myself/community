package com.july.community.controller;

import com.july.community.dto.MessageDTO;
import com.july.community.dto.PaginationDTO;
import com.july.community.model.User;
import com.july.community.service.MessageService;
import com.july.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private MessageService messageService;

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
                //我的问题
                //获取我的问题数据
                PaginationDTO paginationDTO = questionService.getList(user.getId(), page, size);
                model.addAttribute("pagination",paginationDTO);
                model.addAttribute("sectionName","我的提问");
            }
            else if("replies".equals(action)){
                //我的信息
                //获取信息数据
                PaginationDTO paginationDTO = messageService.getList(user.getId(), page, size);
                model.addAttribute("pagination",paginationDTO);
                model.addAttribute("sectionName","最新回复");
            }
        return "profile";
    }
}
