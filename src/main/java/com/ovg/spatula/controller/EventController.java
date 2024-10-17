package com.ovg.spatula.controller;

import com.ovg.spatula.dto.EventRequest;
import com.ovg.spatula.dto.EventResponse;
import com.ovg.spatula.service.EventService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

  private final EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping
  public ResponseEntity<List<EventResponse>> getAllEvents() {
    return ResponseEntity.ok(eventService.getAllEvents());
  }

  @PostMapping
  public ResponseEntity<EventResponse> createEvent(@RequestBody EventRequest eventRequest) {
    return ResponseEntity.ok(eventService.createEvent(eventRequest));
  }

  // 위치 기반 이벤트 검색 API
  @GetMapping("/nearby")
  public ResponseEntity<List<EventResponse>> getEventsNearby(@RequestParam double lng,
      @RequestParam double lat,
      @RequestParam double distance) {
    return ResponseEntity.ok(eventService.getEventsNearby(lng, lat, distance));
  }
}
