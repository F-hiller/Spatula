package com.ovg.spatula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ovg.spatula.entity.User;
import com.ovg.spatula.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  @DisplayName("기기 ID로 사용자 추가")
  public void testAddUser() {
    // given
    String deviceId = "unique-device-id-1234";
    String name = "John Doe";
    User user = User.builder().deviceId(deviceId).name(name).build();

    when(userRepository.findByDeviceId(deviceId)).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(user);

    // when
    User savedUser = userService.addUser(deviceId, name);

    // then
    assertNotNull(savedUser);
    assertEquals(deviceId, savedUser.getDeviceId());
    assertEquals(name, savedUser.getName());
    verify(userRepository, times(1)).findByDeviceId(deviceId);
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  @DisplayName("이미 존재하는 사용자 반환")
  public void testReturnExistingUser() {
    // given
    String deviceId = "existing-device-id";
    String name = "Jane Doe";
    User existingUser = User.builder().deviceId(deviceId).name(name).build();

    when(userRepository.findByDeviceId(deviceId)).thenReturn(Optional.of(existingUser));

    // when
    User resultUser = userService.addUser(deviceId, name);

    // then
    assertNotNull(resultUser);
    assertEquals(deviceId, resultUser.getDeviceId());
    assertEquals(name, resultUser.getName());
    verify(userRepository, times(1)).findByDeviceId(deviceId);
    verify(userRepository, never()).save(any(User.class));  // 저장 메서드는 호출되지 않음
  }

  @Test
  @DisplayName("기기 ID로 사용자 조회")
  public void testFindUserByDeviceId() {
    // given
    String deviceId = "unique-device-id-5678";
    String name = "Alice";
    User user = User.builder().deviceId(deviceId).name(name).build();

    when(userRepository.findByDeviceId(deviceId)).thenReturn(Optional.of(user));

    // when
    Optional<User> foundUser = userService.findUserByDeviceId(deviceId);

    // then
    assertNotNull(foundUser);
    assertEquals(true, foundUser.isPresent());
    assertEquals(deviceId, foundUser.get().getDeviceId());
    verify(userRepository, times(1)).findByDeviceId(deviceId);
  }
}
