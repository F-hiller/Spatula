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
  private final KafkaProducerService kafkaProducerService;

  public ReservationService(ReservationRepository reservationRepository,
      EventRepository eventRepository, KafkaProducerService kafkaProducerService) {
    this.reservationRepository = reservationRepository;
    this.eventRepository = eventRepository;
    this.kafkaProducerService = kafkaProducerService;
  }

  public Reservation reserveEvent(Long eventId, String userEmail) {
    Optional<Event> eventOptional = eventRepository.findById(eventId);
    if (eventOptional.isPresent()) {
      Event event = eventOptional.get();
      event.decreaseAvailableSeats();
      eventRepository.save(event);

      kafkaProducerService.sendReservationMessage(
          "Reservation successful for event: " + event.getName());

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