package com.ovg.spatula.entity;

import com.ovg.spatula.dto.EventRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private LocalDateTime eventDateTime;
  private int totalSeats;
  private int availableSeats;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id")
  private Location location;  // Location과의 관계 설정

  public Event(EventRequest eventRequest, Location location) {
    this.name = eventRequest.getName();
    this.eventDateTime = eventRequest.getEventDateTime();
    this.totalSeats = eventRequest.getTotalSeats();
    this.availableSeats = eventRequest.getTotalSeats();
    this.location = location;
  }

  public void decreaseAvailableSeats() {
    if (this.availableSeats > 0) {
      this.availableSeats--;
    } else {
      throw new RuntimeException("No available seats.");
    }
  }
}
