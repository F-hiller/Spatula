package com.ovg.spatula.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ovg.spatula.dto.request.EventRequest;
import com.ovg.spatula.dto.response.UserResponse;
import com.ovg.spatula.service.EventService;
import com.ovg.spatula.service.UserService;
import com.ovg.spatula.testbase.AddBaseEventsTest;
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
@ActiveProfiles("test")
@Transactional
public class EventControllerTest extends AddBaseEventsTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private EventService eventService;
  @Autowired
  private UserService userService;

  @Test
  @DisplayName("이벤트 추가")
  public void testAddEvent() throws Exception {
    // given
    EventRequest eventRequest = baseEventRequest;
    String code = userService.addUser();
    UserResponse userResponse = userService.getUserInfo(code);

    // when & then (POST 요청으로 이벤트 추가)
    mockMvc.perform(post("/api/events")
            .cookie(new Cookie("code", code))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(eventRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(eventRequest.getName()))
        .andExpect(
            jsonPath("$.basicLocationDto.lng").value(eventRequest.getBasicLocationDto().getLng()))
        .andExpect(
            jsonPath("$.basicLocationDto.lat").value(eventRequest.getBasicLocationDto().getLat()))
        .andExpect(jsonPath("$.userResponse.name").value(userResponse.getName()));
  }

  @Test
  @DisplayName("이벤트 조회")
  public void testGetAllEvents() throws Exception {
    // given
    String code1 = userService.addUser();
    UserResponse userResponse1 = userService.getUserInfo(code1);
    String code2 = userService.addUser();
    UserResponse userResponse2 = userService.getUserInfo(code2);
    eventService.createEvent(baseEventRequest, code1);
    eventService.createEvent(baseDiffPlaceEventRequest, code2);
    eventService.createEvent(fullyBookeEventRequest, code1);
    int eventCnt = 3;

    // when & then (GET 요청으로 모든 이벤트 조회)
    mockMvc.perform(get("/api/events")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(eventCnt))
        .andExpect(jsonPath("$[0].name").value(baseEventRequest.getName()))
        .andExpect(
            jsonPath("$[0].basicLocationDto.lng").value(
                baseEventRequest.getBasicLocationDto().getLng()))
        .andExpect(jsonPath("$[1].name").value(baseDiffPlaceEventRequest.getName()))
        .andExpect(
            jsonPath("$[1].basicLocationDto.lng").value(
                baseDiffPlaceEventRequest.getBasicLocationDto().getLng()))
        .andExpect(jsonPath("$[0].userResponse.name").value(userResponse1.getName()))
        .andExpect(jsonPath("$[1].userResponse.name").value(userResponse2.getName()));
  }
}
