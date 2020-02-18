package com.july.community.mapper;

import com.july.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);

    void incComment(Question record);

    List<Question> selectRelated(Question record);
}