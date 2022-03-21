package com.learn.auth.jwt.jwttokenauthorization.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog")
public class BlogEntity extends BaseEntity {

  private String title;
  private String content;

  @Column(columnDefinition = "char default 'N'")
  private char isDeleted;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "blog_tag", joinColumns = @JoinColumn(name = "blog_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<UserInfoEntity> userInfoEntities;
}