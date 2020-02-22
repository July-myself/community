package com.july.community.service;

import com.july.community.dto.CommentDTO;
import com.july.community.enums.CommentTypeEnum;
import com.july.community.enums.MessageStatusEnum;
import com.july.community.enums.MessageTypeEnum;
import com.july.community.exception.CustomizeErrorCode;
import com.july.community.exception.CustomizeException;
import com.july.community.mapper.*;
import com.july.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageMapper messageMapper;

    public void insert(Comment comment, User commentator) {
        //判断该评论的父存在
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        //判断评论的类型是否存在
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }


        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //评论的回复
            //查询该评论是否存在
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (parentComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //查询出该评论所对应的问题
            Question question = questionMapper.selectByPrimaryKey(parentComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            //给评论添加回复数
            parentComment.setCommentCount(1);
            commentExtMapper.incComment(parentComment);
            //发出信息通知提醒
            createMessage(comment, parentComment.getCommentator(),commentator.getName(), parentComment.getContent(), MessageTypeEnum.REPLY_COMMENT,question.getId());
        }else{
            //问题的回复
            //查询该问题是否存在
            Question parentQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (parentQuestion == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //给问题添加评论数
            parentQuestion.setCommentCount(1);
            questionExtMapper.incComment(parentQuestion);
            //发出信息通知提醒
            createMessage(comment, parentQuestion.getCreator(), commentator.getName(), parentQuestion.getTitle(), MessageTypeEnum.REPLY_QUESTION,parentQuestion.getId());

        }
    }

    private void createMessage(Comment comment, Long receiver, String notifierName, String outerTitle, MessageTypeEnum messageType,Long outerid) {
        //发出信息通知提醒
        Message message = new Message();
        message.setNotifier(comment.getCommentator());
        message.setReceiver(receiver);
        message.setOuterid(outerid);
        message.setStatus(MessageStatusEnum.UNREAD.getStatus());
        message.setType(messageType.getType());
        message.setTimeCreate(System.currentTimeMillis());
        message.setNotifierName(notifierName);
        message.setOuterTitle(outerTitle);
        messageMapper.insert(message);
    }

    public List<CommentDTO> getListByParentId(Long parentId, CommentTypeEnum type) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        //查评论信息
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(parentId)
                                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("time_Modified desc");
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        if (commentList.size()==0){
            //没有评论
            return new ArrayList<>();
        }
        //获得所有去重的评论人id(可以去除重复的数据)
        List<Long> commentatorIds = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toList());
        //查询评论人的信息
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(commentatorIds);
        List<User> userList = userMapper.selectByExample(userExample);
        //将查询到的userList转换成map，以减少接下来循环的层数，减小时间复杂度
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        //转换comment为commentDTO
        List<CommentDTO> commentDTOs = commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOs;

        /*//原来的传统方法
        for (Comment comment : commentList) {
            CommentDTO commentDTO = new CommentDTO();
            User user = userMapper.selectByPrimaryKey(comment.getCommentator());
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(user);
            commentDTOList.add(commentDTO);
        }*/
    }
}
