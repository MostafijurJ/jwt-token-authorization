package com.learn.auth.jwt.jwttokenauthorization.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfo {
  private String userName;
  private String password;
  private String fullName;
  private String email;
}
