package com.july.community.model;

public class Comment {
    private Long id;

    private Long parentId;

    private Integer type;

    private Long commentator;

    private Long timeCreate;

    private Long timeModified;

    private Long likeCount;

    private String content;

    public Comment(Long id, Long parentId, Integer type, Long commentator, Long timeCreate, Long timeModified, Long likeCount, String content) {
        this.id = id;
        this.parentId = parentId;
        this.type = type;
        this.commentator = commentator;
        this.timeCreate = timeCreate;
        this.timeModified = timeModified;
        this.likeCount = likeCount;
        this.content = content;
    }

    public Comment() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCommentator() {
        return commentator;
    }

    public void setCommentator(Long commentator) {
        this.commentator = commentator;
    }

    public Long getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Long timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Long getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(Long timeModified) {
        this.timeModified = timeModified;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}