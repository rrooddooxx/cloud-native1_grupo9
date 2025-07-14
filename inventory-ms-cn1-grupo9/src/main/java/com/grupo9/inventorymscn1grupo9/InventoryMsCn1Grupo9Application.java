package com.grupo9.inventorymscn1grupo9;

import com.grupo9.inventorymscn1grupo9.config.KafkaServicePropertiesConfig;
import com.grupo9.inventorymscn1grupo9.config.KafkaTopicPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
  KafkaTopicPropertiesConfig.class,
  KafkaServicePropertiesConfig.class
})
public class InventoryMsCn1Grupo9Application {

  public static void main(String[] args) {
    SpringApplication.run(InventoryMsCn1Grupo9Application.class, args);
  }
}
