package com.ovg.spatula.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ovg.spatula.entity.User;
import com.ovg.spatula.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")  // 테스트용 프로파일 사용
@Transactional
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;  // JSON 변환을 위해 사용

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("사용자 추가 통합 테스트")
  public void testAddUser() throws Exception {
    // given
    String deviceId = "test-device-id";
    String name = "John Doe";

    // when & then (POST 요청으로 사용자 추가)
    mockMvc.perform(post("/api/users")
            .param("deviceId", deviceId)
            .param("name", name)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.deviceId").value(deviceId))
        .andExpect(jsonPath("$.name").value(name));
  }

  @Test
  @DisplayName("기기 ID로 사용자 조회 통합 테스트")
  public void testGetUserByDeviceId() throws Exception {
    // given
    String deviceId = "test-device-id";
    String name = "Jane Doe";
    User user = User.builder().deviceId(deviceId).name(name).build();
    userRepository.save(user);

    // when & then (GET 요청으로 사용자 조회)
    mockMvc.perform(get("/api/users/{deviceId}", deviceId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.deviceId").value(deviceId))
        .andExpect(jsonPath("$.name").value(name));
  }
}
