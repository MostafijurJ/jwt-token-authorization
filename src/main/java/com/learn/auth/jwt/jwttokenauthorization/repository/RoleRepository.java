package com.learn.auth.jwt.jwttokenauthorization.repository;

import com.learn.auth.jwt.jwttokenauthorization.entity.RoleEntity;
import com.learn.auth.jwt.jwttokenauthorization.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  Boolean existsByName(ERole name);
  RoleEntity findByName(ERole name);
}
