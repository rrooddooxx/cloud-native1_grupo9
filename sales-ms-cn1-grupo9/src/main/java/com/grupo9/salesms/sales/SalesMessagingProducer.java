package com.grupo9.salesms.sales;

import com.grupo9.salesms.config.KafkaProperties;
import com.grupo9.salesms.config.SalesRabbitMessagingProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SalesMessagingProducer {
  private final AmqpTemplate amqpTemplate;
  private final KafkaTemplate<String, Sale> kafkaTemplate;
  private final SalesRabbitMessagingProperties salesRabbitMessagingProperties;
  private final KafkaProperties kafkaProperties;

  public void sendRabbitMqSalesMessage(Sale sale) {
    try {
      amqpTemplate.convertAndSend(
          salesRabbitMessagingProperties.getExchange(),
          salesRabbitMessagingProperties.getRoutingKey(),
          sale.toString());
    } catch (Exception e) {
      log.error(
          "Error sending message to RabbitMq, Exchange: {}, Error: {}",
          salesRabbitMessagingProperties.getExchange(),
          e.getMessage());
    }
  }

  public void sendKafkaSaleFinishedMessage(Sale sale) {
    try {
      kafkaTemplate.send(kafkaProperties.getSaleFinishedTopic(), sale.getTransactionId(), sale);
    } catch (Exception e) {
      log.error(
          "Error sending message to Kafka, Topic: {}, Error: {}",
          kafkaProperties.getSaleFinishedTopic(),
          e.getMessage());
    }
  }
}
