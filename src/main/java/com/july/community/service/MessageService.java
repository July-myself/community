package com.july.community.service;

import com.july.community.dto.MessageDTO;
import com.july.community.dto.PaginationDTO;
import com.july.community.dto.QuestionDTO;
import com.july.community.enums.MessageStatusEnum;
import com.july.community.enums.MessageTypeEnum;
import com.july.community.exception.CustomizeErrorCode;
import com.july.community.exception.CustomizeException;
import com.july.community.mapper.MessageMapper;
import com.july.community.mapper.UserMapper;
import com.july.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO getList(Long userId, Integer page, Integer size) {
        PaginationDTO<MessageDTO> paginationDTO = new PaginationDTO<>();
        MessageExample example = new MessageExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("time_create desc");
        Integer totalCount = (int) messageMapper.countByExample(example);
        paginationDTO.setPagination(totalCount, page, size);
        Integer totalPage = paginationDTO.getTotalPage();
        //限定页码范围
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage && totalPage != 0) {
            page = totalPage;
        }
        //获取message所有对象
        Integer offset = size * (page - 1);
        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andReceiverEqualTo(userId);

        List<Message> messages = messageMapper.selectByExampleWithRowbounds(messageExample,
                new RowBounds(offset, size));//该list存从数据库中查到的message对象
        if (messages.size() == 0){
            return paginationDTO;
        }
        List<MessageDTO> messageDTOS = new ArrayList<>(); //该list存要返回的messageDTO对象
        //循环获取
        for (Message message : messages) {
            MessageDTO messageDTO = new MessageDTO();
            BeanUtils.copyProperties(message,messageDTO);
            messageDTO.setTypeName(MessageTypeEnum.nameOfType(message.getType()));
            messageDTOS.add(messageDTO);
        }
        paginationDTO.setData(messageDTOS);
        return paginationDTO;
    }

    //获取用户未读信息数
    public Long unreadCount(Long receiverId) {
        MessageExample example = new MessageExample();
        example.createCriteria().andReceiverEqualTo(receiverId)
        .andStatusEqualTo(MessageStatusEnum.UNREAD.getStatus());
        return messageMapper.countByExample(example);
    }

    //获取点击的信息
    public MessageDTO getMessageDTO(Long id, User user) {
        //获取点击的问题
        Message message = messageMapper.selectByPrimaryKey(id);
        if (message == null){
            throw new CustomizeException(CustomizeErrorCode.MESSAGE_NOT_FOUND);
        }
        //核对查看权限
        if (message.getReceiver() != user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_MESSAGE_FAIL);
        }
        //标记为已读
        message.setStatus(MessageStatusEnum.READ.getStatus());
        messageMapper.updateByPrimaryKey(message);
        //获取这条回复，得到其所对应的问题
        MessageDTO messageDTO= new MessageDTO();
        BeanUtils.copyProperties(message,messageDTO);
        messageDTO.setTypeName(MessageTypeEnum.nameOfType(message.getType()));
        return messageDTO;
    }
}
