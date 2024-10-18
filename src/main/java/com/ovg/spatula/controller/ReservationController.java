package com.ovg.spatula.controller;

import com.ovg.spatula.dto.response.ReservationResponse;
import com.ovg.spatula.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping("/{eventId}")
  public ResponseEntity<ReservationResponse> reserveEvent(@PathVariable Long eventId,
      @RequestParam String userEmail) {
    return ResponseEntity.ok(reservationService.reserveEvent(eventId, userEmail));
  }
}