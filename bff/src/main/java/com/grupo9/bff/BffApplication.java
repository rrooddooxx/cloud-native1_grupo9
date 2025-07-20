package com.grupo9.bff;

import com.grupo9.bff.config.SalesRabbitMessagingProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({SalesRabbitMessagingProperties.class})
public class BffApplication {

  public static void main(String[] args) {
    SpringApplication.run(BffApplication.class, args);
  }
}
