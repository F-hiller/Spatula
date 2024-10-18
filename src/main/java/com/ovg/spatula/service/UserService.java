package com.ovg.spatula.service;

import com.ovg.spatula.dto.UserResponse;
import com.ovg.spatula.entity.User;
import com.ovg.spatula.repository.UserRepository;
import com.ovg.spatula.util.NicknameGenerator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserResponse addUser() {
    User user = User.builder()
        .name(NicknameGenerator.generateNickname())
        .code(UUID.randomUUID().toString())
        .build();
    return new UserResponse(userRepository.save(user));
  }

  public UserResponse getUserInfo(String code) {
    Optional<User> optionalUser = userRepository.findByCode(code);
    if (optionalUser.isEmpty()) {
      throw new NoSuchElementException("No such user with code.");
    }
    return new UserResponse(optionalUser.get());
  }
}