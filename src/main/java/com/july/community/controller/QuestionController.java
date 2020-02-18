package com.july.community.controller;

import com.july.community.dto.CommentDTO;
import com.july.community.dto.QuestionDTO;
import com.july.community.enums.CommentTypeEnum;
import com.july.community.service.CommentService;
import com.july.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){

        //获取问题列表
        QuestionDTO questionDTO =  questionService.getListById(id);
        //获取问题的评论
        List<CommentDTO> commentList = commentService.getListByParentId(id, CommentTypeEnum.QUESTION);
        //获取相关问题
        List<QuestionDTO> relatedQuestionDTOs = questionService.getRelated(questionDTO);
        //添加浏览数
        questionService.incView(id);

        model.addAttribute("question",questionDTO);
        model.addAttribute("commentList",commentList);
        model.addAttribute("relatedQuestionList",relatedQuestionDTOs);
        return "question";
    }
}
