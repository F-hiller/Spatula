package com.ovg.spatula.dto.response;

import com.ovg.spatula.entity.Reservation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationResponse {

  private Long id;
  private EventResponse eventResponse;
  private String userEmail;

  public ReservationResponse(Reservation reservation) {
    this.id = reservation.getId();
    this.eventResponse = new EventResponse(reservation.getEvent());
    this.userEmail = reservation.getUserEmail();
  }
}
