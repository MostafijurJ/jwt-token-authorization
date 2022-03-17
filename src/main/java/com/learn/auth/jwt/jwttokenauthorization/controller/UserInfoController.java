package com.learn.auth.jwt.jwttokenauthorization.controller;

import com.learn.auth.jwt.jwttokenauthorization.models.UserInfo;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import com.learn.auth.jwt.jwttokenauthorization.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

  @Autowired
  private UserDetailsService userDetailsService;

  @PostMapping("/create")
  public Response<UserInfo> create(@RequestBody UserInfo userInfo){
    return userDetailsService.createUser(userInfo);
  }

}

