package com.learn.auth.jwt.jwttokenauthorization.repository;

import com.learn.auth.jwt.jwttokenauthorization.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity,Integer> {

  Boolean existsByUserName(String username);
  Boolean existsByEmail(String email);
  UserInfoEntity findByUserName(String username);

}