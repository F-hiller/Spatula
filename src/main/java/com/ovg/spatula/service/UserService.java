package com.ovg.spatula.service;

import com.ovg.spatula.entity.User;
import com.ovg.spatula.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // 사용자를 추가하는 메서드
  public User addUser(String deviceId, String name) {
    // 기기 ID를 통해 사용자가 존재하는지 확인
    Optional<User> existingUser = userRepository.findByDeviceId(deviceId);
    if (existingUser.isPresent()) {
      return existingUser.get();  // 이미 존재하는 경우 해당 사용자 반환
    }

    // 새로운 사용자 생성
    User user = User.builder()
        .deviceId(deviceId)
        .name(name)
        .build();
    return userRepository.save(user);
  }

  // 사용자를 기기 ID로 조회
  public Optional<User> findUserByDeviceId(String deviceId) {
    return userRepository.findByDeviceId(deviceId);
  }
}