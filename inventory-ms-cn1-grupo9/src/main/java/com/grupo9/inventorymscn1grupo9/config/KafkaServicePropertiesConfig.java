package com.grupo9.inventorymscn1grupo9.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class KafkaServicePropertiesConfig {
  private List<String> bootstrapServers;

  private Consumer consumer;
  private Producer producer;

  @Data
  public static class Consumer {
    private List<String> bootstrapServers;
    private String groupId;
  }

  @Data
  public static class Producer {
    private List<String> bootstrapServers;
  }
}
