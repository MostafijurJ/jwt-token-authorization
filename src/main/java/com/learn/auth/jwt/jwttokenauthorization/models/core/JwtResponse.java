package com.learn.auth.jwt.jwttokenauthorization.models.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse implements Serializable {

  private static final long serialVersionUID = -8091879091924046844L;

  private final String token;
  private String username;
  private Collection<GrantedAuthority> roles;

  public JwtResponse(String jwtToken) {

    this.token = jwtToken;

  }

  public String getToken() {

    return this.token;

  }

}
