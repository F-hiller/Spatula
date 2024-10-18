package com.ovg.spatula.dto;

import com.ovg.spatula.entity.Event;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventResponse {

  private Long id;
  private String name;
  private LocalDateTime eventDateTime;
  private int totalSeats;
  private int availableSeats;
  private BasicLocationDto basicLocationDto;
  private UserResponse userResponse;

  public EventResponse(Event event) {
    this.id = event.getId();
    this.name = event.getName();
    this.eventDateTime = event.getEventDateTime();
    this.totalSeats = event.getTotalSeats();
    this.availableSeats = event.getAvailableSeats();
    this.basicLocationDto = new BasicLocationDto(event.getLocation());
    this.userResponse = new UserResponse(event.getOrganizer());
  }
}