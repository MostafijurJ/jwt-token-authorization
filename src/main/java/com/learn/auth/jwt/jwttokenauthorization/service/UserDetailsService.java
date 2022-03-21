package com.learn.auth.jwt.jwttokenauthorization.service;

import com.learn.auth.jwt.jwttokenauthorization.entity.RoleEntity;
import com.learn.auth.jwt.jwttokenauthorization.entity.UserInfoEntity;
import com.learn.auth.jwt.jwttokenauthorization.enums.ERole;
import com.learn.auth.jwt.jwttokenauthorization.enums.ResponseCode;
import com.learn.auth.jwt.jwttokenauthorization.exceptions.ValidationException;
import com.learn.auth.jwt.jwttokenauthorization.models.UserInfo;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import com.learn.auth.jwt.jwttokenauthorization.repository.RoleRepository;
import com.learn.auth.jwt.jwttokenauthorization.repository.UserInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Component
public class UserDetailsService
    implements org.springframework.security.core.userdetails.UserDetailsService {

  @Autowired
  private UserInfoRepository userInfoRepository;
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserInfoEntity user = userInfoRepository.findByUserName(username);
    if (ObjectUtils.isEmpty(user)) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
    return new User(user.getUserName(), user.getPassword(), getAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Set<RoleEntity> roles) {
    Set<GrantedAuthority> authorities = new HashSet<>();
    for (RoleEntity role : roles) {
      authorities.add(new GrantedAuthority() {
        @Override
        public String getAuthority() {
          return role.getName().getRoleName();
        }
      });
    }
    return authorities;
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
    userInfoEntity.setRoles(setUserRole(userInfo.getRoles()));
    UserInfoEntity savedUserInfoEntity = saveUser(userInfoEntity);

    BeanUtils.copyProperties(savedUserInfoEntity, userInfo, "password");
    response.setResponseCode(ResponseCode.SUCCESS.getCode());
    response.setItems(userInfo);
    return response;
  }

  private UserInfoEntity saveUser(UserInfoEntity userInfoEntity) {
    return userInfoRepository.save(userInfoEntity);
  }

  public Response<UserInfo> updateUser(UserInfo userInfo) {
    Response<UserInfo> response = new Response<>();
    UserInfoEntity userInfoEntity = userInfoRepository.findByUserName(userInfo.getUserName());
    if (ObjectUtils.isEmpty(userInfoEntity)) {
      throw new ValidationException("User not found with username: " + userInfo.getUserName());
    }
    BeanUtils.copyProperties(userInfo, userInfoEntity, "password");
    userInfoEntity.setRoles(setUserRole(userInfo.getRoles()));
    UserInfoEntity savedUserInfoEntity = saveUser(userInfoEntity);
    BeanUtils.copyProperties(savedUserInfoEntity, userInfo, "password");
    response.setResponseCode(ResponseCode.SUCCESS.getCode());
    response.setItems(userInfo);
    return response;
  }

  public Response<List<UserInfo>> getAllActiveUsers(){
    Response<List<UserInfo>> response = new Response<>();
    List<UserInfoEntity> userInfoEntities = userInfoRepository.findByIsActive(Boolean.TRUE);
    if(ObjectUtils.isEmpty(userInfoEntities)) {
      throw new ValidationException("No user found");
    }
    response.setResponseCode(ResponseCode.SUCCESS.getCode());
    response.setItems(convertToUserInfo(userInfoEntities));
    return response;
  }


  public Response<UserInfo> deleteUser(String userName) {
    Response<UserInfo> response = new Response<>();
    UserInfoEntity userInfoEntity = userInfoRepository.findByUserName(userName);
    if (ObjectUtils.isEmpty(userInfoEntity)) {
      throw new ValidationException("User not found with username: " + userName);
    }
    userInfoEntity.setActive(Boolean.FALSE);
    userInfoRepository.save(userInfoEntity);
    response.setResponseCode(ResponseCode.SUCCESS.getCode());
    return response;
  }


  private String getEncodedPassword(String password) {
    return new BCryptPasswordEncoder().encode(password);
  }

  private Set<RoleEntity> setUserRole(List<String> roles) {
    Set<RoleEntity> roleEntities = new HashSet<>();
    roles.forEach(role -> {
      RoleEntity roleEntity = new RoleEntity();
      if (role.equalsIgnoreCase("ADMIN")) {
        roleEntities.add(getRole(ERole.ADMIN));
      }
      if (role.equalsIgnoreCase("MODERATOR")) {
        roleEntities.add(getRole(ERole.MODERATOR));
      }
      if (role.equalsIgnoreCase("USER")) {
        roleEntities.add(getRole(ERole.USER));
      } else {
        throw new ValidationException("Role not found, please enter valid role like: ADMIN, MODERATOR, USER");
      }

    });
    return roleEntities;
  }

  private RoleEntity getRole(ERole role) {
    RoleEntity roleEntity = roleRepository.findByName(role);
    if (ObjectUtils.isEmpty(roleEntity))
      throw new ValidationException("Role not found in system! Please check role name");
    return roleEntity;
  }

  private List<UserInfo> convertToUserInfo(List<UserInfoEntity> userInfoEntities) {
    List<UserInfo> userInfos =  new ArrayList<>();
    for (UserInfoEntity userInfoEntity : userInfoEntities) {
      UserInfo userInfo = new UserInfo();
      BeanUtils.copyProperties(userInfoEntity, userInfo);
      userInfo.setRoles(getRoleList(userInfoEntity.getRoles()));
      userInfos.add(userInfo);
    }
    return userInfos;
  }

  private List<String> getRoleList(Set<RoleEntity> roleEntities) {
    List<String> roles = new ArrayList<>();
    for (RoleEntity roleEntity : roleEntities) {
      roles.add(roleEntity.getName().toString());
    }
    return roles;
  }


}
