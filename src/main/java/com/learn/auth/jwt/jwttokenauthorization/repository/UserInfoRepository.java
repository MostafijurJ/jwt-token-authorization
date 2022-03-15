package com.learn.auth.jwt.jwttokenauthorization.repository;

import com.learn.auth.jwt.jwttokenauthorization.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {

  Boolean existsByUsername(String username);
  UserInfo findByUsername(String username);


}