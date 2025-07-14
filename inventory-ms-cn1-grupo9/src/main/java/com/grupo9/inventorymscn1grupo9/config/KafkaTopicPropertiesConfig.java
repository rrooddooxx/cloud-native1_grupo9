package com.grupo9.inventorymscn1grupo9.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "queues-kafka")
@Data
public class KafkaTopicPropertiesConfig {
  private String stockTopic;
  private String saleFinishedTopic;
}
