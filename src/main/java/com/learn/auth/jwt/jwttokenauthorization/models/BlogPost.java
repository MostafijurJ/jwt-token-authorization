package com.learn.auth.jwt.jwttokenauthorization.models;

import lombok.Data;

@Data
public class BlogPost {
    private Integer id;
    private String title;
    private String content;
    private UserInfo userInfo;
}
