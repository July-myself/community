package com.july.community.controller;

import com.july.community.dto.CommentCreateDTO;
import com.july.community.dto.CommentDTO;
import com.july.community.dto.ResultDTO;
import com.july.community.enums.CommentTypeEnum;
import com.july.community.exception.CustomizeErrorCode;
import com.july.community.model.Comment;
import com.july.community.model.User;
import com.july.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        //获取登录的用户信息
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_CONTENT_NULL);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setTimeCreate(System.currentTimeMillis());
        comment.setTimeModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment,user);
        return ResultDTO.successOf();
    }

    //@RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO getSubComments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOs = commentService.getListByParentId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.successOf(commentDTOs);
    }


}
