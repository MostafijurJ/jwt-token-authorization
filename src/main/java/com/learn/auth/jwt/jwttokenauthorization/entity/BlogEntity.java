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
@SequenceGenerator(name = "blog_seq", sequenceName = "blog_seq", allocationSize = 1)
public class BlogEntity {

  @Id
  @GeneratedValue(generator = "blog_seq", strategy = GenerationType.SEQUENCE)
  private int id;
  private String title;
  private String content;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "blog_tag", joinColumns = @JoinColumn(name = "blog_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<UserInfoEntity> userInfoEntities;
}