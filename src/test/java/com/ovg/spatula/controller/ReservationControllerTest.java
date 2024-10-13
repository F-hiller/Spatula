package com.ovg.spatula.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ovg.spatula.entity.Event;
import com.ovg.spatula.repository.EventRepository;
import com.ovg.spatula.repository.ReservationRepository;
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
public class ReservationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private ReservationRepository reservationRepository;

  @Test
  @DisplayName("예약 추가 통합 테스트")
  public void testReserveEvent() throws Exception {
    // given
    Event event = Event.builder()
        .name("Test Event")
        .eventDateTime(LocalDateTime.of(2024, 12, 15, 10, 0))
        .totalSeats(100)
        .availableSeats(100)
        .build();
    eventRepository.save(event);

    String userEmail = "test@example.com";

    // when & then (POST 요청으로 이벤트 예약)
    mockMvc.perform(post("/api/reservations/{eventId}", event.getId())
            .param("userEmail", userEmail)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userEmail").value(userEmail))
        .andExpect(jsonPath("$.event.name").value("Test Event"))
        .andExpect(jsonPath("$.event.availableSeats").value(99));  // 좌석 감소 확인
  }
}
