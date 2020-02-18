package com.july.community.dto;

import com.july.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    private Long parentId;

    private Integer type;

    private Long commentator;

    private Long timeCreate;

    private Long timeModified;

    private Long likeCount;

    private String content;

    private Integer commentCount;

    private User user;
}
