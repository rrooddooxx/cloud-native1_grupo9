package com.grupo9.bff.sales;

import com.grupo9.bff.config.SalesRabbitMessagingProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SalesMessagingProducer {

  private final AmqpTemplate amqpTemplate;
  private final SalesRabbitMessagingProperties rabbitMessagingProperties;

  public void sendRabbitMqSalesMessage(SaleEvent sale) {
    try {
      amqpTemplate.convertAndSend(
              rabbitMessagingProperties.getExchange(),
              rabbitMessagingProperties.getRoutingKey(),
              sale.toString());
    } catch (Exception e) {
      log.error(
              "Error sending message to RabbitMq, Exchange: {}, Error: {}",
              rabbitMessagingProperties.getExchange(),
              e.getMessage());
    }
  }

}
