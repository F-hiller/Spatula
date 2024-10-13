package com.ovg.spatula.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

  private static final String TOPIC = "reservation-topic";

  private final KafkaTemplate<String, String> kafkaTemplate;

  public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendReservationMessage(String message) {
    kafkaTemplate.send(TOPIC, message);
  }
}