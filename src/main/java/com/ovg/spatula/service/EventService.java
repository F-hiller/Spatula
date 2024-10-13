package com.ovg.spatula.service;

import com.ovg.spatula.entity.Event;
import com.ovg.spatula.repository.EventRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  private final EventRepository eventRepository;

  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Cacheable(value = "events", key = "#id")
  public Optional<Event> getEventById(Long id) {
    return eventRepository.findById(id);
  }

  public List<Event> getAllEvents() {
    return eventRepository.findAll();
  }

  public Event createEvent(String name, LocalDateTime eventDateTime, int totalSeats) {
    // 빌더 패턴으로 객체 생성
    Event event = Event.builder()
        .name(name)
        .eventDateTime(eventDateTime)
        .totalSeats(totalSeats)
        .availableSeats(totalSeats)
        .build();
    return eventRepository.save(event);
  }
}
