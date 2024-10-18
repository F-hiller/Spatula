package com.ovg.spatula.service;

import com.ovg.spatula.dto.BasicLocationDto;
import com.ovg.spatula.dto.request.EventRequest;
import com.ovg.spatula.dto.response.EventResponse;
import com.ovg.spatula.entity.Event;
import com.ovg.spatula.entity.Location;
import com.ovg.spatula.entity.User;
import com.ovg.spatula.exception.exceptions.NoSuchUserException;
import com.ovg.spatula.repository.EventRepository;
import com.ovg.spatula.repository.LocationRepository;
import com.ovg.spatula.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

  private final EventRepository eventRepository;
  private final LocationRepository locationRepository;
  private final UserRepository userRepository;
  private final GeometryFactory geometryFactory = new GeometryFactory();  // 공간 데이터를 생성하기 위한 팩토리

  public EventService(EventRepository eventRepository, LocationRepository locationRepository,
      UserRepository userRepository) {
    this.eventRepository = eventRepository;
    this.locationRepository = locationRepository;
    this.userRepository = userRepository;
  }

  @Cacheable(value = "events", key = "#id")
  public Optional<EventResponse> getEventById(Long id) {
    return eventRepository.findById(id).map(EventResponse::new);
  }

  public List<EventResponse> getAllEvents() {
    return eventRepository.findAll().stream().map(EventResponse::new).collect(Collectors.toList());
  }

  @Transactional
  public EventResponse createEvent(EventRequest eventRequest, String code) {
    BasicLocationDto basicLocationDto = eventRequest.getBasicLocationDto();
    Point point = geometryFactory.createPoint(
        new Coordinate(basicLocationDto.getLng(), basicLocationDto.getLat()));
    Location location = locationRepository.save(new Location(basicLocationDto, point));
    User user = userRepository.findByCode(code).orElseThrow(NoSuchUserException::new);
    Event event = eventRepository.save(new Event(eventRequest, location, user));

    return new EventResponse(event);
  }

  // 위치 기반 이벤트 검색 (캐싱 적용)
//  @Cacheable(value = "eventsNearby", key = "#lat + '-' + #lng + '-' + #distance")
  public List<EventResponse> getEventsNearby(double lng, double lat, double distance) {
    return eventRepository.findEventsWithinDistance(lng, lat, distance).stream()
        .map(EventResponse::new).collect(Collectors.toList());
  }
}