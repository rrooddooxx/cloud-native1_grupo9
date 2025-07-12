package com.grupo9.bff.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class KafkaCustomProperties {
  private List<String> bootstrapServers;
  private String consumerGroupId;
}
