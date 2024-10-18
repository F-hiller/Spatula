package com.ovg.spatula.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ovg.spatula.repository.UserRepository;
import com.ovg.spatula.service.UserService;
import jakarta.servlet.http.Cookie;
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
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("사용자 추가")
  public void testAddUser() throws Exception {
    // given

    // when & then (POST 요청으로 사용자 추가)
    mockMvc.perform(get("/api/user/code")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 정보 조회")
  public void testGetUserInfo() throws Exception {
    // given
    String code = userService.addUser();

    // when & then (POST 요청으로 사용자 추가)
    mockMvc.perform(get("/api/user/info")
            .cookie(new Cookie("code", code))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 정보 조회 실패")
  public void testFailGetUserInfo() throws Exception {
    // given

    // when & then (POST 요청으로 사용자 추가)
    mockMvc.perform(get("/api/user/info")
            .cookie(new Cookie("code", "Meaningless code."))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
