package com.ovg.spatula.testbase;

import com.ovg.spatula.dto.BasicLocationDto;
import com.ovg.spatula.dto.EventRequest;
import com.ovg.spatula.entity.Event;
import com.ovg.spatula.entity.Location;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public abstract class AddBaseEventsTest {

  protected GeometryFactory geometryFactory = new GeometryFactory();

  //기본 이벤트
  protected Event baseEvent;
  //장소는 다르며 날짜가 같은 이벤트
  protected Event baseDiffPlaceEvent;
  //장소는 같지만 날짜가 다르며 자리가 가득찬 이벤트
  protected Event fullyBookedEvent;
  protected EventRequest baseEventRequest;
  protected EventRequest baseDiffPlaceEventRequest;
  protected EventRequest fullyBookeEventRequest;
  protected Location daeguLocation;
  protected Location incheonLocation;
  protected BasicLocationDto daeguBasicLocationDto;
  protected BasicLocationDto incheonBasicLocationDto;

  @BeforeEach
  public void setBaseEvents() {
    daeguBasicLocationDto = BasicLocationDto.builder()
        .address("대구광역시 북구 호암로 15")
        .lng(35.883570)
        .lat(128.592248)
        .name("대구오페라하우스")
        .build();

    incheonBasicLocationDto = BasicLocationDto.builder()
        .address("인천광역시 중구 공항문화로 127 인스파이어 아레나")
        .lng(35.887851)
        .lat(128.592744)
        .name("인스파이어 아레나")
        .build();

    baseEventRequest = EventRequest.builder()
        .eventDateTime(LocalDateTime.of(2024, 12, 15, 10, 0))
        .name("Event 1")
        .totalSeats(150)
        .basicLocationDto(daeguBasicLocationDto)
        .build();

    baseDiffPlaceEventRequest = EventRequest.builder()
        .eventDateTime(LocalDateTime.of(2024, 12, 15, 10, 0))
        .name("Event 2")
        .totalSeats(100)
        .basicLocationDto(incheonBasicLocationDto)
        .build();

    fullyBookeEventRequest = EventRequest.builder()
        .eventDateTime(LocalDateTime.of(2025, 1, 27, 17, 0))
        .name("Event 3")
        .totalSeats(150)
        .basicLocationDto(daeguBasicLocationDto)
        .build();

    Point point1 = geometryFactory.createPoint(
        new Coordinate(daeguBasicLocationDto.getLng(), daeguBasicLocationDto.getLat()));
    Point point2 = geometryFactory.createPoint(
        new Coordinate(incheonBasicLocationDto.getLng(), incheonBasicLocationDto.getLat()));

    daeguLocation = new Location(daeguBasicLocationDto, point1);
    incheonLocation = new Location(incheonBasicLocationDto, point2);

    baseEvent = new Event(baseEventRequest, daeguLocation);
    baseDiffPlaceEvent = new Event(baseDiffPlaceEventRequest, incheonLocation);
    fullyBookedEvent = Event.builder()
        .location(daeguLocation)
        .name(fullyBookeEventRequest.getName())
        .totalSeats(fullyBookeEventRequest.getTotalSeats())
        .availableSeats(0)
        .eventDateTime(fullyBookeEventRequest.getEventDateTime())
        .build();
  }
}
