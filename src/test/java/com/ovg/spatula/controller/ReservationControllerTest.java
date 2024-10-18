package com.ovg.spatula.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ovg.spatula.entity.Event;
import com.ovg.spatula.repository.EventRepository;
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
public class ReservationControllerTest extends AddBaseEventsTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EventRepository eventRepository;

  @Test
  @DisplayName("예약 추가")
  public void testReserveEvent() throws Exception {
    Event event = baseEvent;
    eventRepository.save(event);

    String userEmail = "test@example.com";

    // when & then (POST 요청으로 이벤트 예약)
    mockMvc.perform(post("/api/reservations/{eventId}", event.getId())
            .param("userEmail", userEmail)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userEmail").value(userEmail))
        .andExpect(jsonPath("$.eventResponse.name").value(event.getName()))
        .andExpect(jsonPath("$.eventResponse.availableSeats").value(
            event.getTotalSeats() - 1));
  }
}
