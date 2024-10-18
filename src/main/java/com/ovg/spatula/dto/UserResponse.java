package com.ovg.spatula.dto;

import com.ovg.spatula.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

  private String email;
  private String name;

  public UserResponse(User user) {
    this.email = user.getEmail();
    this.name = user.getName();
  }
}
