package com.learn.auth.jwt.jwttokenauthorization.controller;

import com.learn.auth.jwt.jwttokenauthorization.models.core.JwtRequest;
import com.learn.auth.jwt.jwttokenauthorization.models.core.JwtResponse;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import com.learn.auth.jwt.jwttokenauthorization.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController implements AuthenticationApi{

  @Autowired
  private AccessService accessService;

  public Response<JwtResponse> createAuthenticationToken(
      @RequestBody JwtRequest authenticationRequest) throws Exception {
    return accessService.authenticateUser(authenticationRequest);
  }
}
