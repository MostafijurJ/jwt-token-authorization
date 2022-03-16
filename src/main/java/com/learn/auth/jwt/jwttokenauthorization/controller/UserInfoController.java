package com.learn.auth.jwt.jwttokenauthorization.controller;

import com.learn.auth.jwt.jwttokenauthorization.models.UserInfo;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import com.learn.auth.jwt.jwttokenauthorization.repository.UserInfoRepository;
import com.learn.auth.jwt.jwttokenauthorization.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {


  final private UserInfoRepository userInfoRepository;
  @Autowired
  private UserDetailsService userDetailsService;


  public UserInfoController(UserInfoRepository userInfoRepository) {
    this.userInfoRepository = userInfoRepository;
  }


  @PostMapping("/create")
  public Response<UserInfo> create(@RequestBody UserInfo userInfo){
    return userDetailsService.createUser(userInfo);
  }

}

