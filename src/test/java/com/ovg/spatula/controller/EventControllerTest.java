package com.ovg.spatula.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ovg.spatula.entity.Event;
import com.ovg.spatula.repository.EventRepository;
import java.time.LocalDateTime;
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
public class EventControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EventRepository eventRepository;

  @Test
  @DisplayName("이벤트 추가 통합 테스트")
  public void testAddEvent() throws Exception {
    // given
    String eventName = "Test Event";
    LocalDateTime eventDateTime = LocalDateTime.of(2024, 12, 15, 10, 0);
    int totalSeats = 100;

    Event event = Event.builder()
        .name(eventName)
        .eventDateTime(eventDateTime)
        .totalSeats(totalSeats)
        .availableSeats(totalSeats)
        .build();

    // when & then (POST 요청으로 이벤트 추가)
    mockMvc.perform(post("/api/events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(event)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(eventName))
        .andExpect(jsonPath("$.totalSeats").value(totalSeats));
  }

  @Test
  @DisplayName("이벤트 조회 통합 테스트")
  public void testGetAllEvents() throws Exception {
    // given
    Event event1 = Event.builder().name("Event 1")
        .eventDateTime(LocalDateTime.of(2024, 12, 15, 10, 0))
        .totalSeats(100)
        .availableSeats(100)
        .build();
    Event event2 = Event.builder().name("Event 2")
        .eventDateTime(LocalDateTime.of(2024, 12, 16, 14, 0))
        .totalSeats(200)
        .availableSeats(200)
        .build();
    eventRepository.save(event1);
    eventRepository.save(event2);

    // when & then (GET 요청으로 모든 이벤트 조회)
    mockMvc.perform(get("/api/events")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].name").value("Event 1"))
        .andExpect(jsonPath("$[1].name").value("Event 2"));
  }
}
