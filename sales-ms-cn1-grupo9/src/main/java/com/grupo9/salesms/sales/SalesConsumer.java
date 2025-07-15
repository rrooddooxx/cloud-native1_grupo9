package com.grupo9.salesms.sales;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SalesConsumer {

  private final SalesService salesService;

  /* @RabbitListener(queues = "${queue-rabbitmq.sales.queue}")
  public void receiveMessage(@Payload CreateSaleRequestDto message) {
    log.info("Received message: {}", message);
    try {
      log.info("Message processed successfully");
    } catch (Exception e) {
      log.error("Error processing message: {}", e.getMessage(), e);
    }
  }*/
}
