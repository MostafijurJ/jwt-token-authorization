package com.learn.auth.jwt.jwttokenauthorization.controller;

import com.learn.auth.jwt.jwttokenauthorization.models.core.JwtRequest;
import com.learn.auth.jwt.jwttokenauthorization.models.core.JwtResponse;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags = "Authentication")
public interface AuthenticationApi {
  @Operation(summary = "Authenticate user")
  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public Response<JwtResponse> createAuthenticationToken(
      @RequestBody JwtRequest authenticationRequest) throws Exception;

}
