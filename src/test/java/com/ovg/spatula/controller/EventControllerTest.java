package com.ovg.spatula.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ovg.spatula.dto.EventRequest;
import com.ovg.spatula.service.EventService;
import com.ovg.spatula.testbase.AddBaseEventsTest;
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

  @Test
  @DisplayName("이벤트 추가 통합 테스트")
  public void testAddEvent() throws Exception {
    // given
    EventRequest eventRequest = baseEventRequest;

    // when & then (POST 요청으로 이벤트 추가)
    mockMvc.perform(post("/api/events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(eventRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(eventRequest.getName()))
        .andExpect(
            jsonPath("$.basicLocationDto.lng").value(eventRequest.getBasicLocationDto().getLng()))
        .andExpect(
            jsonPath("$.basicLocationDto.lat").value(eventRequest.getBasicLocationDto().getLat()));
  }

  @Test
  @DisplayName("이벤트 조회 통합 테스트")
  public void testGetAllEvents() throws Exception {
    // given
    eventService.createEvent(baseEventRequest);
    eventService.createEvent(baseDiffPlaceEventRequest);
    eventService.createEvent(fullyBookeEventRequest);
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
                baseDiffPlaceEventRequest.getBasicLocationDto().getLng()));
  }
}
