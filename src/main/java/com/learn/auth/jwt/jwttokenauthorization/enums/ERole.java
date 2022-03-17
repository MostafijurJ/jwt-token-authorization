package com.learn.auth.jwt.jwttokenauthorization.enums;

public enum ERole {
  USER("ROLE_USER"),
  MODERATOR("ROLE_MODERATOR"),
  ADMIN("ROLE_ADMIN");

  private String roleName;

  ERole(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleName() {
    return roleName;
  }
}
