package com.grupo9.salesms;

import com.grupo9.salesms.config.KafkaProperties;
import com.grupo9.salesms.config.SalesRabbitMessagingProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
  SalesRabbitMessagingProperties.class,
  KafkaProperties.class
})
public class SalesMsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SalesMsApplication.class, args);
  }
}
