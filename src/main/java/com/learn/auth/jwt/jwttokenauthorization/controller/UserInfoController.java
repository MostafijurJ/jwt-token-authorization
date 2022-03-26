package com.learn.auth.jwt.jwttokenauthorization.controller;

import com.learn.auth.jwt.jwttokenauthorization.models.UserInfo;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import com.learn.auth.jwt.jwttokenauthorization.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController implements UserInfoApi {

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  public Response<UserInfo> createUser(UserInfo userInfo) {
    return userDetailsService.createUser(userInfo);
  }

  @Override
  public Response<UserInfo> getUserInfo(UserInfo userInfo) {
    return null;
  }

  @Override
  public Response<UserInfo> updateUser(UserInfo userInfo) {
    return userDetailsService.updateUser(userInfo);
  }

  @Override
  public Response<UserInfo> deleteUser(String userId) {
    return null;
  }
}

