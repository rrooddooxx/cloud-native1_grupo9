package com.skravetz.cn1grupo9.controller;

import com.skravetz.cn1grupo9.config.RabbitMQConfig;
import com.skravetz.cn1grupo9.dto.PriceChangeDTO;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/price-changes")
@RequiredArgsConstructor
@Slf4j
public class PriceChangeController {

  private final AmqpTemplate amqpTemplate;

  @PostMapping("/update")
  public ResponseEntity<String> updatePrice(@RequestBody PriceChangeDTO priceChangeDTO) {
    try {
      if (priceChangeDTO.getChangeDate() == null) {
        priceChangeDTO.setChangeDate(LocalDateTime.now());
      }

      log.info("Processing price change for product ID: {}", priceChangeDTO.getProductId());

      amqpTemplate.convertAndSend(
          RabbitMQConfig.PRICE_CHANGE_EXCHANGE,
          RabbitMQConfig.PRICE_CHANGE_ROUTING_KEY,
          priceChangeDTO);

      log.info("Price change sent to queue for product: {}", priceChangeDTO.getProductId());

      return ResponseEntity.ok(
          "Price change processed successfully for product: " + priceChangeDTO.getProductId());

    } catch (Exception e) {
      log.error("Error processing price change for product: {}", priceChangeDTO.getProductId(), e);
      return ResponseEntity.internalServerError()
          .body("Error processing price change: " + e.getMessage());
    }
  }
}
