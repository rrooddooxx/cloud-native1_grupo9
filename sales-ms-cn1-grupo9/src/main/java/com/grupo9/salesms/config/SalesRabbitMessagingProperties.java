package com.grupo9.salesms.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "queue-rabbitmq.sales")
@Getter
@Setter
public class SalesRabbitMessagingProperties {
  private String exchange;
  private String queue;
  private String routingKey;
}
