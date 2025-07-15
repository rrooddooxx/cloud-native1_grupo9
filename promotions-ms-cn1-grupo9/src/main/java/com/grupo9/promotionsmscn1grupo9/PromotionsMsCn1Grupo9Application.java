package com.grupo9.promotionsmscn1grupo9;

import com.grupo9.promotionsmscn1grupo9.config.KafkaProperties;
import com.grupo9.promotionsmscn1grupo9.config.KafkaTopicProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({KafkaProperties.class, KafkaTopicProperties.class})
public class PromotionsMsCn1Grupo9Application {

  public static void main(String[] args) {
    SpringApplication.run(PromotionsMsCn1Grupo9Application.class, args);
  }
}
