package com.learn.auth.jwt.jwttokenauthorization.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity {
  private static final long serialVersionUID = 1L;

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "defaultGenerator")
  protected long id;

  @Column(name = "creation_date", nullable = false)
  private Date creationDate;

  @Column(name = "updated_date")
  private Date updatedDate;


}
