package com.grupo9.inventorymscn1grupo9.stock;

import com.grupo9.inventorymscn1grupo9.config.KafkaTopicPropertiesConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockMessagingProducer {
  private final KafkaTemplate<String, StockEvent> kafkaTemplate;
  private final KafkaTopicPropertiesConfig properties;

  public void sendKafkaStockUpdatedMessage(StockEvent event) {
    try {
      kafkaTemplate.send(properties.getStockTopic(), event.getProductId().toString(), event);
    } catch (Exception e) {
      log.error(
          "Error sending message to Kafka, Topic: {}, Error: {}",
          properties.getStockTopic(),
          e.getMessage());
    }
  }
}
