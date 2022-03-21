package com.learn.auth.jwt.jwttokenauthorization.controller;

import com.learn.auth.jwt.jwttokenauthorization.models.UserInfo;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "User API")
@RequestMapping("/user")
@RestController
public interface UserInfoApi {

  @Operation(summary = "create user")
  @PostMapping("/create")
  public Response<UserInfo> createUser(@RequestBody UserInfo userInfo);

  @Operation(summary = "get user")
  @PutMapping("/get")
  public Response<UserInfo> getUserInfo(@RequestBody UserInfo userInfo);

  @Operation(summary = "update user")
  @PostMapping("/update")
  public Response<UserInfo> updateUser(@RequestBody UserInfo userInfo);

  @Operation(summary = "delete user")
  @DeleteMapping("/{id}")
  public Response<UserInfo> deleteUser(@PathVariable("id") String id);

}
