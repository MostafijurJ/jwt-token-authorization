package com.learn.auth.jwt.jwttokenauthorization.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
  private String userName;
  private String password;
  private String fullName;
  private String email;
  private List<String> roles;
}
