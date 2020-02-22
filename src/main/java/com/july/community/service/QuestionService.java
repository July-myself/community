package com.july.community.service;

import com.july.community.dto.PaginationDTO;
import com.july.community.dto.QuestionDTO;
import com.july.community.model.Question;
import com.july.community.exception.CustomizeErrorCode;
import com.july.community.exception.CustomizeException;
import com.july.community.mapper.QuestionExtMapper;
import com.july.community.mapper.QuestionMapper;
import com.july.community.mapper.UserMapper;
import com.july.community.model.QuestionExample;
import com.july.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO getList(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(totalCount, page, size);
        Integer totalPage = paginationDTO.getTotalPage();
        //限定页码范围
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        //获取question所有对象
        Integer offset = size * (page - 1);

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("time_modified desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));//该list存从数据库中查到的question对象
        List<QuestionDTO> questionDTOList = new ArrayList<>(); //该list存要返回的questionDTO对象
        //循环questions,获取user
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            //转换成questionDTO
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO); //加入要返回的questionDYO列表中
        }
        paginationDTO.setData(questionDTOList);


        return paginationDTO;
    }

    public PaginationDTO getList(Long userId, Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(example);
        paginationDTO.setPagination(totalCount, page, size);
        Integer totalPage = paginationDTO.getTotalPage();
        //限定页码范围
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage && totalPage != 0) {
            page = totalPage;
        }
        //获取question所有对象
        Integer offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,
                new RowBounds(offset, size));//该list存从数据库中查到的question对象
        List<QuestionDTO> questionDTOList = new ArrayList<>(); //该list存要返回的questionDTO对象
        //循环questions,获取user
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            //转换成questionDTO
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO); //加入要返回的questionDYO列表中
        }
        paginationDTO.setData(questionDTOList);


        return paginationDTO;
    }

    public QuestionDTO getListById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        //获取user
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //插入
            question.setTimeCreate(System.currentTimeMillis());
            question.setTimeModified(System.currentTimeMillis());
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setViewCount(0);
            questionMapper.insert(question);
        } else {
            //更新

            question.setTimeModified(System.currentTimeMillis());
            Question updateQuestion = new Question();
            updateQuestion.setTimeModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(question, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.UPDATE_FAIL_QUESTION_NUT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question record = new Question();
        record.setId(id);
        record.setViewCount(1);
        questionExtMapper.incView(record);
    }

    //获取相关问题
    public List<QuestionDTO> getRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())){
            //若该问题没有tag，就没有相关问题
            return new ArrayList<>();
        }
        //将tag中的,替换成|以供正则使用
        String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));//将tags用|拼接起来
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);
        List<Question> relatedQuestions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> relatedQuestionDTOs = relatedQuestions.stream().map(q -> {
            QuestionDTO relatedQuestionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,relatedQuestionDTO);
            return relatedQuestionDTO;
        }).collect(Collectors.toList());
        return relatedQuestionDTOs;
    }
}
