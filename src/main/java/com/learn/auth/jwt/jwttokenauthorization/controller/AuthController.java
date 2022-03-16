package com.learn.auth.jwt.jwttokenauthorization.controller;

import com.learn.auth.jwt.jwttokenauthorization.config.JwtToken;
import com.learn.auth.jwt.jwttokenauthorization.models.JwtRequest;
import com.learn.auth.jwt.jwttokenauthorization.models.JwtResponse;
import com.learn.auth.jwt.jwttokenauthorization.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
@CrossOrigin
public class AuthController {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtToken jwtToken;

  @Autowired private JwtUserDetailsService jwtUserDetailsService;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
      throws Exception {

    var isAuthenticate =
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    if(!(isAuthenticate.getStatusCode().is2xxSuccessful())) {
      return ResponseEntity.ok("Authentication failed");

    }
    final UserDetails userDetails =
        jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String token = jwtToken.generateToken(userDetails);

    return ResponseEntity.ok(new JwtResponse(token));

  }

  private ResponseEntity<?> authenticate(String username, String password) throws Exception {

    try {

    authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    return ResponseEntity.ok("Successfully authenticated");

    } catch (DisabledException e) {

      throw new Exception("USER_DISABLED", e);

    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }
}