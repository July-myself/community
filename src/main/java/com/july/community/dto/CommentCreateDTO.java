package com.july.community.dto;

import lombok.Data;

/**
 * 书写评论内容时页面传递过来的DTO
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
