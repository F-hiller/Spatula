package com.ovg.spatula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ovg.spatula.entity.Event;
import com.ovg.spatula.entity.Reservation;
import com.ovg.spatula.repository.EventRepository;
import com.ovg.spatula.repository.ReservationRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private EventRepository eventRepository;

  @Mock
  private KafkaProducerService kafkaProducerService;

  @InjectMocks
  private ReservationService reservationService;

  @Test
  @DisplayName("이벤트 예약 성공")
  public void testReserveEventSuccess() {
    // given
    Event event = Event.builder().name("Test Event").totalSeats(100).availableSeats(50).build();
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(reservationRepository.save(any(Reservation.class))).thenAnswer(
        invocation -> invocation.getArgument(0));

    // when
    Reservation reservation = reservationService.reserveEvent(1L, "test@example.com");

    // then
    assertNotNull(reservation);
    assertEquals("test@example.com", reservation.getUserEmail());
    assertEquals(event, reservation.getEvent());
    verify(eventRepository, times(1)).findById(1L);
    verify(reservationRepository, times(1)).save(any(Reservation.class));
  }

  @Test
  @DisplayName("이벤트 좌석 부족으로 예약 실패")
  public void testReserveEventNoAvailableSeats() {
    // given
    Event event = Event.builder().name("Test Event").totalSeats(100).availableSeats(0).build();
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

    // when
    Exception exception = assertThrows(RuntimeException.class, () -> {
      reservationService.reserveEvent(1L, "test@example.com");
    });

    // then
    assertEquals("No available seats.", exception.getMessage());
    verify(eventRepository, times(1)).findById(1L);
    verify(reservationRepository, never()).save(any(Reservation.class));
  }

  @Test
  @DisplayName("존재하지 않는 이벤트 예약 시도")
  public void testReserveEventNotFound() {
    // given
    when(eventRepository.findById(1L)).thenReturn(Optional.empty());

    // when
    Exception exception = assertThrows(RuntimeException.class, () -> {
      reservationService.reserveEvent(1L, "test@example.com");
    });

    // then
    assertEquals("Event not found.", exception.getMessage());
    verify(eventRepository, times(1)).findById(1L);
    verify(reservationRepository, never()).save(any(Reservation.class));
  }
}
