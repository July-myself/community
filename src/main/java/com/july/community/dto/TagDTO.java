package com.july.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
    private String categoryName; //分类
    private List<String> tagList;

}
