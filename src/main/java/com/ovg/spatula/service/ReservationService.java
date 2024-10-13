package com.ovg.spatula.service;

import com.ovg.spatula.entity.Event;
import com.ovg.spatula.entity.Reservation;
import com.ovg.spatula.repository.EventRepository;
import com.ovg.spatula.repository.ReservationRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final EventRepository eventRepository;

  public ReservationService(ReservationRepository reservationRepository,
      EventRepository eventRepository) {
    this.reservationRepository = reservationRepository;
    this.eventRepository = eventRepository;
  }

  public Reservation reserveEvent(Long eventId, String userEmail) {
    Optional<Event> eventOptional = eventRepository.findById(eventId);
    if (eventOptional.isPresent()) {
      Event event = eventOptional.get();
      event.decreaseAvailableSeats();  // 좌석 감소 로직 호출
      eventRepository.save(event);

      // 빌더 패턴으로 예약 생성
      Reservation reservation = Reservation.builder()
          .event(event)
          .userEmail(userEmail)
          .build();
      return reservationRepository.save(reservation);
    } else {
      throw new RuntimeException("Event not found.");
    }
  }
}