package com.july.community.service;

import com.july.community.dto.QuestionDTO;
import com.july.community.mapper.QuestionMapper;
import com.july.community.mapper.UserMapper;
import com.july.community.model.Question;
import com.july.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    
    @Autowired
    private QuestionMapper questionMapper;
    
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> getList() {
        //获取question所有对象
        List<Question> questions = questionMapper.getList(); //该list存从数据库中查到的question对象
        List<QuestionDTO> questionDTOList = new ArrayList<>(); //该list存要返回的questionDTO对象
        //循环questions,获取user
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            //转换成questionDTO
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO); //加入要返回的questionDYO列表中
        }
        return questionDTOList;
    }
}
