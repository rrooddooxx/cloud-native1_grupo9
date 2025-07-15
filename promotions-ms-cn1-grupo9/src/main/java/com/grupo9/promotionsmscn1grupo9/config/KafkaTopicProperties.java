package com.grupo9.promotionsmscn1grupo9.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "queues")
@Data
public class KafkaTopicProperties {
  private String sales;
  private String stock;
}
