package com.july.community.model;

import lombok.Data;

@Data
public class User{
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long timeCreate;
    private Long timeModified;
    private String avatarUrl;

    public User( ) {
    }

    public User( String name, String accountId, String token, String avatarUrl) {
        this.name = name;
        this.accountId = accountId;
        this.token = token;
        this.avatarUrl = avatarUrl;
    }
}
