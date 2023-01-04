package com.VishalGhanghav.notificationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotificationServiceApplication.class, args);
  }
  
  @KafkaListener(topics = "notificationTopic")
  public void handleNotiication(OrderPlacedEvent orderPlacedEvent) {
	  //Send out email notification
	  log.info("Recieved notification for order - {}",orderPlacedEvent.getOrderNumber());
  }

}
