package com.learn.auth.jwt.jwttokenauthorization.entity;

import com.learn.auth.jwt.jwttokenauthorization.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
@SequenceGenerator(name="role_seq",sequenceName="ROLE_SEQ", allocationSize=1)
public class RoleEntity {
  @Id
  @GeneratedValue(generator = "role_seq")
  private Integer id;

  @Enumerated(EnumType.STRING)
  private ERole name;

}

