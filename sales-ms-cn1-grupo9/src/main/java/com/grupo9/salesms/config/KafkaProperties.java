package com.grupo9.salesms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "queue-kafka")
@Data
public class KafkaProperties {
  private String bootstrapServers;
  private String saleFinishedTopic;
}
