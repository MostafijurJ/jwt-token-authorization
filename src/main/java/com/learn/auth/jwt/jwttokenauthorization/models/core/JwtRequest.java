package com.learn.auth.jwt.jwttokenauthorization.models.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable {

  private static final long serialVersionUID = 5926468583005150707L;

  private String username;

  private String password;

}
