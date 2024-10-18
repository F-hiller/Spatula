package com.ovg.spatula.service;

import com.ovg.spatula.dto.response.UserResponse;
import com.ovg.spatula.entity.User;
import com.ovg.spatula.exception.exceptions.NoSuchCodeException;
import com.ovg.spatula.repository.UserRepository;
import com.ovg.spatula.util.NicknameGenerator;
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

  /**
   * @return 생성된 사용자 Code
   */
  public String addUser() {
    String code = UUID.randomUUID().toString();
    User user = User.builder()
        .name(NicknameGenerator.generateNickname())
        .code(code)
        .build();
    userRepository.save(user);

    return code;
  }

  public UserResponse getUserInfo(String code) {
    Optional<User> optionalUser = userRepository.findByCode(code);
    if (optionalUser.isEmpty()) {
      throw new NoSuchCodeException();
    }
    return new UserResponse(optionalUser.get());
  }
}