package com.july.community.controller;

import com.july.community.dto.MessageDTO;
import com.july.community.enums.MessageTypeEnum;
import com.july.community.model.User;
import com.july.community.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/message/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          HttpServletRequest request) {
        //验证登录状态
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        //获取点击的信息
        MessageDTO messageDTO = messageService.getMessageDTO(id, user);
        if (MessageTypeEnum.REPLY_COMMENT.getType() == messageDTO.getType()
                || MessageTypeEnum.REPLY_QUESTION.getType() == messageDTO.getType()) {
            //跳转到相应的问题页面
            return "redirect:/question/" + messageDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
