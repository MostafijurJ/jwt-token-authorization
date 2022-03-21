package com.learn.auth.jwt.jwttokenauthorization.entity;

import com.learn.auth.jwt.jwttokenauthorization.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {
  @Enumerated(EnumType.STRING)
  private ERole name;

}

