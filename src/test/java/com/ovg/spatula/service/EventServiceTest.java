package com.ovg.spatula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ovg.spatula.dto.response.EventResponse;
import com.ovg.spatula.entity.Event;
import com.ovg.spatula.entity.Location;
import com.ovg.spatula.entity.User;
import com.ovg.spatula.repository.EventRepository;
import com.ovg.spatula.repository.LocationRepository;
import com.ovg.spatula.repository.UserRepository;
import com.ovg.spatula.testbase.AddBaseEventsTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest extends AddBaseEventsTest {

  @Mock
  private EventRepository eventRepository;

  @Mock
  private LocationRepository locationRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private EventService eventService;

  @Test
  @DisplayName("모든 이벤트 조회")
  public void testGetAllEvents() {
    // given
    List<Event> listEvent = Arrays.asList(baseEvent, baseDiffPlaceEvent);
    when(eventRepository.findAll()).thenReturn(listEvent);

    // when
    List<EventResponse> events = eventService.getAllEvents();

    // then
    assertNotNull(events);
    assertEquals(listEvent.size(), events.size());
    verify(eventRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("이벤트 생성")
  public void testCreateEvent() {
    // given
    Event event = fullyBookedEvent;
    Location location = daeguLocation;
    User user = dummyUser1;
    when(userRepository.findByCode(any(String.class))).thenReturn(Optional.of(user));
    when(locationRepository.save(any(Location.class))).thenReturn(location);
    when(eventRepository.save(any(Event.class))).thenReturn(event);

    // when
    EventResponse savedEvent = eventService.createEvent(fullyBookeEventRequest, userCode1);

    // then
    assertNotNull(savedEvent);
    assertEquals(fullyBookeEventRequest.getName(), savedEvent.getName());
    assertEquals(fullyBookeEventRequest.getTotalSeats(), savedEvent.getTotalSeats());
    assertEquals(fullyBookeEventRequest.getBasicLocationDto().getLat(),
        savedEvent.getBasicLocationDto().getLat());
    assertEquals(fullyBookeEventRequest.getBasicLocationDto().getLng(),
        savedEvent.getBasicLocationDto().getLng());

    verify(locationRepository, times(1)).save(any(Location.class));
    verify(eventRepository, times(1)).save(any(Event.class));
  }

  @Test
  @DisplayName("근처 이벤트 조회 테스트")
  public void testGetEventsNearby() {
    // given
    double lng = 35.883570;
    double lat = 128.59224;
    double distance = 1000; // 1km

    List<Event> events = Arrays.asList(baseEvent, fullyBookedEvent);

    when(eventRepository.findEventsWithinDistance(eq(lng), eq(lat), eq(distance))).thenReturn(
        events);

    // when
    List<EventResponse> nearbyEvents = eventService.getEventsNearby(lng, lat, distance);

    // then
    assertNotNull(nearbyEvents);
    assertEquals(events.size(), nearbyEvents.size());
    verify(eventRepository, times(1)).findEventsWithinDistance(eq(lng), eq(lat), eq(distance));
  }
}
