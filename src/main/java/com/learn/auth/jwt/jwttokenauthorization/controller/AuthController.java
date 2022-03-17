package com.learn.auth.jwt.jwttokenauthorization.controller;

import com.learn.auth.jwt.jwttokenauthorization.models.core.JwtRequest;
import com.learn.auth.jwt.jwttokenauthorization.models.core.JwtResponse;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import com.learn.auth.jwt.jwttokenauthorization.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {

  @Autowired
  private AccessService accessService;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public Response<JwtResponse> createAuthenticationToken(
      @RequestBody JwtRequest authenticationRequest) throws Exception {
    return accessService.authenticateUser(authenticationRequest);
  }
}
