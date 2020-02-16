package com.july.community.controller;

import com.july.community.dto.QuestionDTO;
import com.july.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){

        QuestionDTO questionDTO =  questionService.getListById(id);
        //添加浏览数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
