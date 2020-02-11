package com.july.community.dto;

import com.july.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long timeCreate;
    private Long timeModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private User user;

}
