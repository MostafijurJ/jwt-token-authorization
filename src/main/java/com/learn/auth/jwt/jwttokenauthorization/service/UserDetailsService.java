package com.learn.auth.jwt.jwttokenauthorization.service;

import com.learn.auth.jwt.jwttokenauthorization.entity.UserInfoEntity;
import com.learn.auth.jwt.jwttokenauthorization.enums.ResponseCode;
import com.learn.auth.jwt.jwttokenauthorization.exceptions.ValidationException;
import com.learn.auth.jwt.jwttokenauthorization.models.UserInfo;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import com.learn.auth.jwt.jwttokenauthorization.repository.UserInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

@Component
public class UserDetailsService
    implements org.springframework.security.core.userdetails.UserDetailsService {

  @Autowired private UserInfoRepository userInfoRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserInfoEntity user = userInfoRepository.findByUserName(username);
    if (ObjectUtils.isEmpty(user)) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
    return new User(user.getUserName(), user.getPassword(), new ArrayList<>());
  }


  public Response<UserInfo> createUser(UserInfo userInfo) {
    Response<UserInfo> response = new Response<>();

    if (userInfoRepository.existsByUserName(userInfo.getUserName())) {
      throw new ValidationException("Username already existed");
    }
    if (userInfoRepository.existsByEmail(userInfo.getEmail())) {
      throw new ValidationException("Email already existed");
    }
    String password = userInfo.getPassword();

    UserInfoEntity userInfoEntity = new UserInfoEntity();
    BeanUtils.copyProperties(userInfo, userInfoEntity);

    String encodedPassword = getEncodedPassword(password);

    userInfoEntity.setPassword(encodedPassword);

    UserInfoEntity savedUserInfoEntity = saveUser(userInfoEntity);

    BeanUtils.copyProperties(savedUserInfoEntity, userInfo, "password");
    response.setResponseCode(ResponseCode.SUCCESS.getCode());
    response.setItems(userInfo);
    return response;
  }

  private UserInfoEntity saveUser(UserInfoEntity userInfoEntity) {
    return userInfoRepository.save(userInfoEntity);
  }

  private String getEncodedPassword(String password) {
    return new BCryptPasswordEncoder().encode(password);
  }
}
