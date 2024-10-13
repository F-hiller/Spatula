package com.ovg.spatula.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

  @KafkaListener(topics = "reservation-topic", groupId = "reservation_group")
  public void consumeReservationMessage(String message) {
    log.info("Received message: {}", message);
  }
}