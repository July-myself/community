package com.july.community.model;

public class User {
    private Integer id;

    private String accountId;

    private String name;

    private String token;

    private Long timeCreate;

    private Long timeModified;

    private String bio;

    private String avatarUrl;

    public User(Integer id, String accountId, String name, String token, Long timeCreate, Long timeModified, String bio, String avatarUrl) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.token = token;
        this.timeCreate = timeCreate;
        this.timeModified = timeModified;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio == null ? null : bio.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }
}