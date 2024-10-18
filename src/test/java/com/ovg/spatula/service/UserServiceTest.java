package com.ovg.spatula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ovg.spatula.dto.response.UserResponse;
import com.ovg.spatula.entity.User;
import com.ovg.spatula.exception.exceptions.NoSuchCodeException;
import com.ovg.spatula.repository.UserRepository;
import com.ovg.spatula.testbase.AddBaseEventsTest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends AddBaseEventsTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  @DisplayName("사용자 추가 테스트")
  public void testAddUser() {
    // given
    when(userRepository.save(any(User.class))).thenReturn(dummyUser1);

    // when
    String resultCode = userService.addUser();

    // then
    verify(userRepository, times(1)).save(any(User.class));
    assertEquals(36, resultCode.length());
  }

  @Test
  @DisplayName("사용자 정보 가져오기 성공 테스트")
  public void testGetUserInfoSuccess() {
    // given
    when(userRepository.findByCode(userCode1)).thenReturn(Optional.of(dummyUser1));

    // when
    UserResponse userResponse = userService.getUserInfo(userCode1);

    // then
    verify(userRepository, times(1)).findByCode(userCode1);
    assertEquals(dummyUser1.getName(), userResponse.getName());
  }

  @Test
  @DisplayName("존재하지 않는 사용자 코드로 정보 가져오기 실패 테스트")
  public void testGetUserInfoFailure() {
    // given
    when(userRepository.findByCode(userCode1)).thenReturn(Optional.empty());

    // when & then
    assertThrows(NoSuchCodeException.class, () -> {
      userService.getUserInfo(userCode1);
    });

    verify(userRepository, times(1)).findByCode(userCode1);
  }
}
