package com.learn.auth.jwt.jwttokenauthorization.service;

import com.learn.auth.jwt.jwttokenauthorization.config.JwtToken;
import com.learn.auth.jwt.jwttokenauthorization.enums.ResponseCode;
import com.learn.auth.jwt.jwttokenauthorization.models.JwtRequest;
import com.learn.auth.jwt.jwttokenauthorization.models.JwtResponse;
import com.learn.auth.jwt.jwttokenauthorization.models.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccessService {

  @Autowired private AuthenticationManager authenticationManager;
  @Autowired private JwtToken jwtToken;
  @Autowired private JwtUserDetailsService jwtUserDetailsService;

  public Response<JwtResponse> authenticateUser(JwtRequest jwtRequest) throws Exception {
    Response<JwtResponse> response = new Response<>();
    var authResp = this.authenticate(jwtRequest);
    if (!(authResp.getResponseCode() == ResponseCode.SUCCESS.getCode())) {
      response.setResponseCode(ResponseCode.BAD_REQUEST.getCode());
      response.setResponseMessages(authResp.getResponseMessages());
      return response;
    }
    final UserDetails userDetails =
        jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
    final String token = jwtToken.generateToken(userDetails);
    log.trace("Token generated successfully"+token);
    response.setResponseCode(ResponseCode.SUCCESS.getCode());
    response.setItems(new JwtResponse(token));
    return response;
  }

  private Response<Authentication> authenticate(JwtRequest jwtRequest) {
    Response<Authentication> response = new Response<>();
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              jwtRequest.getUsername(), jwtRequest.getPassword()));
      response.setResponseCode(ResponseCode.SUCCESS.getCode());
      return response;
    } catch (DisabledException | BadCredentialsException e) {
      e.printStackTrace();
      log.error("Invalid username/password supplied "+e.getMessage());
      response.setResponseCode(ResponseCode.BAD_REQUEST.getCode());
      response.setResponseMessages(List.of("Invalid username or password"));
      return response;
    }
  }
}
