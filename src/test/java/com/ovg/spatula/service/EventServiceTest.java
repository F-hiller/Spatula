package com.ovg.spatula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ovg.spatula.entity.Event;
import com.ovg.spatula.repository.EventRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

  @Mock
  private EventRepository eventRepository;

  @InjectMocks
  private EventService eventService;

  @Test
  @DisplayName("모든 이벤트 조회")
  public void testGetAllEvents() {
    // given
    Event event1 = Event.builder().name("Event 1").eventDateTime(LocalDateTime.now())
        .totalSeats(100).availableSeats(100).build();
    Event event2 = Event.builder().name("Event 2").eventDateTime(LocalDateTime.now())
        .totalSeats(200).availableSeats(200).build();
    when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

    // when
    List<Event> events = eventService.getAllEvents();

    // then
    assertNotNull(events);
    assertEquals(2, events.size());
    verify(eventRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("이벤트 생성")
  public void testCreateEvent() {
    // given
    Event event = Event.builder().name("New Event").eventDateTime(LocalDateTime.now())
        .totalSeats(150).availableSeats(150).build();
    when(eventRepository.save(any(Event.class))).thenReturn(event);

    // when
    Event savedEvent = eventService.createEvent("New Event", LocalDateTime.now(), 150);

    // then
    assertNotNull(savedEvent);
    assertEquals("New Event", savedEvent.getName());
    assertEquals(150, savedEvent.getTotalSeats());
    verify(eventRepository, times(1)).save(any(Event.class));
  }
}
