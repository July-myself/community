package com.july.community.controller;

import com.july.community.dto.PaginationDTO;
import com.july.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page, //分页参数：第几页
                        @RequestParam(name = "size",defaultValue = "5") Integer size, //分页参数：每页多少条数据
                        @RequestParam(name = "search",required = false) String search
    ){
        //显示问题列表
        PaginationDTO pagination = questionService.getList(search,page,size);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        return "index";
    }
}
