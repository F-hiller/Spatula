package com.ovg.spatula.controller;

import com.ovg.spatula.dto.EventRequest;
import com.ovg.spatula.entity.Event;
import com.ovg.spatula.service.EventService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public ResponseEntity<Event> createEvent(@RequestBody EventRequest eventRequest) {
    Event event = eventService.createEvent(
        eventRequest.getName(),
        eventRequest.getEventDateTime(),
        eventRequest.getTotalSeats()
    );
    return ResponseEntity.ok(event);
  }
}
