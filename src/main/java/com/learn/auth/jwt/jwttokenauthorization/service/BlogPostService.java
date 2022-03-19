package com.learn.auth.jwt.jwttokenauthorization.service;

import com.learn.auth.jwt.jwttokenauthorization.entity.BlogEntity;
import com.learn.auth.jwt.jwttokenauthorization.entity.RoleEntity;
import com.learn.auth.jwt.jwttokenauthorization.entity.UserInfoEntity;
import com.learn.auth.jwt.jwttokenauthorization.enums.ResponseCode;
import com.learn.auth.jwt.jwttokenauthorization.exceptions.ValidationException;
import com.learn.auth.jwt.jwttokenauthorization.models.BlogPost;
import com.learn.auth.jwt.jwttokenauthorization.models.UserInfo;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import com.learn.auth.jwt.jwttokenauthorization.repository.BlogRepository;
import com.learn.auth.jwt.jwttokenauthorization.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class BlogPostService {
  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private UserInfoRepository userInfoRepository;

  public Response<BlogPost> createPost(BlogPost blogPost) {
    Response<BlogPost> response = new Response<>();
    BlogEntity blogEntity = new BlogEntity();
    UserDetails userDetails = getCurrentUserDetails();
    BeanUtils.copyProperties(blogPost, blogEntity);
    blogEntity.setUserInfoEntities(makeUserInfoEntity(userDetails));

    try {
      blogRepository.save(blogEntity);
      response.setResponseCode(ResponseCode.SUCCESS.getCode());
      BeanUtils.copyProperties(blogEntity,blogPost);
      blogPost.setUserInfo(entityToDomain(blogEntity.getUserInfoEntities()));
      response.setItems(blogPost);
    }catch (Exception e){
     log.error("Error while saving blog post", e);
     response.setResponseCode(ResponseCode.NOT_FOUND.getCode());
     response.setResponseMessages(List.of("Error while saving blog post"));
     e.printStackTrace();
    }
    return response;
  }

  private UserDetails getCurrentUserDetails() {
    UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return principal;
  }

  private Set<UserInfoEntity> makeUserInfoEntity(UserDetails userDetails){
    Set<UserInfoEntity> entitySet = new HashSet<>();
    UserInfoEntity entity = userInfoRepository.findByUserName(userDetails.getUsername());
    if(ObjectUtils.isEmpty(entity)){
      throw  new ValidationException("Your token is expired now");
    }
    entitySet.add(entity);
    return entitySet;
  }

  private UserInfo entityToDomain(Set<UserInfoEntity> entity){
    UserInfo info = new UserInfo();
    var userInfoEntity = entity.stream().findFirst()
        .orElseThrow(() -> new ValidationException("User not found"));
    List<String> roleList = new ArrayList<>();

    if(ObjectUtils.isEmpty(userInfoEntity)){
      throw new ValidationException("Empty role list can't be mapped");
    }

    for(RoleEntity role : userInfoEntity.getRoles()){
      roleList.add(role.getName().getRoleName());
    }

    info.setUserName( userInfoEntity.getUserName());
    info.setFullName(userInfoEntity.getFullName());
    info.setEmail(userInfoEntity.getEmail());
    info.setRoles(roleList);
    return info;
  }

}
