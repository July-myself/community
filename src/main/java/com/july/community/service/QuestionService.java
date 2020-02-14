package com.july.community.service;

import com.july.community.dto.PaginationDTO;
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

    public PaginationDTO getList(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        Integer totalPage = paginationDTO.getTotalPage();
        //限定页码范围
        if (page < 1){ page = 1;}
        if(page > totalPage){ page = totalPage;}
        //获取question所有对象
        Integer offset = size * (page-1);
        List<Question> questions = questionMapper.getList(offset,size); //该list存从数据库中查到的question对象
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
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }

    public PaginationDTO getList(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalCount,page,size);
        Integer totalPage = paginationDTO.getTotalPage();
        //限定页码范围
        if (page < 1){ page = 1;}
        if(page > totalPage && totalPage!=0){ page = totalPage;}
        //获取question所有对象
        Integer offset = size * (page-1);
        List<Question> questions = questionMapper.getListByUserId(userId, offset,size); //该list存从数据库中查到的question对象
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
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }

    public QuestionDTO getListById(Integer id) {
        Question question = questionMapper.getListById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        //获取user
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            //插入
            question.setTimeCreate(System.currentTimeMillis());
            question.setTimeModified(System.currentTimeMillis());
            questionMapper.create(question);
        }else{
            //更新
            question.setTimeModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
