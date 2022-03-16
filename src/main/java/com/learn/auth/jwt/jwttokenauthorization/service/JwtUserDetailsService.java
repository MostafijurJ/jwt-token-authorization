package com.learn.auth.jwt.jwttokenauthorization.service;

import com.learn.auth.jwt.jwttokenauthorization.entity.UserInfo;
import com.learn.auth.jwt.jwttokenauthorization.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired private UserInfoRepository userInfoRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserInfo user = userInfoRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), new ArrayList<>());
  }
}
