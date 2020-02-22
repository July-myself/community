package com.july.community.mapper;

import com.july.community.dto.QuestionQueryDTO;
import com.july.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);

    void incComment(Question record);

    List<Question> selectRelated(Question record);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearchWithRowbounds(QuestionQueryDTO questionQueryDTO);
}