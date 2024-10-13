package com.ovg.spatula.controller;

import com.ovg.spatula.entity.Event;
import com.ovg.spatula.service.EventService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<List<Event>> getAllEvents() {
    return ResponseEntity.ok(eventService.getAllEvents());
  }

  @PostMapping
  public ResponseEntity<Event> createEvent(@RequestParam String name,
      @RequestParam LocalDateTime eventDateTime,
      @RequestParam int totalSeats) {
    Event event = eventService.createEvent(name, eventDateTime, totalSeats);
    return ResponseEntity.ok(event);
  }
}
