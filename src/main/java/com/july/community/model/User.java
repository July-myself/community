package com.july.community.model;

public class User{
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long timeCreate;
    private Long timeModified;
    private String avatarUrl;

    public User() {
    }

    public User( String name, String accountId, String token, Long timeCreate, Long timeModified, String avatarUrl) {
        this.name = name;
        this.accountId = accountId;
        this.token = token;
        this.timeCreate = timeCreate;
        this.timeModified = timeModified;
        this.avatarUrl = avatarUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accountId='" + accountId + '\'' +
                ", token='" + token + '\'' +
                ", timeCreate=" + timeCreate +
                ", timeModified=" + timeModified +
                '}';
    }
}
