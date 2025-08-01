package com.grupo9.promotionsmscn1grupo9.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class KafkaProperties {
  private String bootstrapServers;
  private Consumer consumer;

  @Data
  public static class Consumer {
    private List<String> bootstrapServers;
    private String groupId;
  }
}
