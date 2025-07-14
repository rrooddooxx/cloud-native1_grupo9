package com.skravetz.cn1grupo9.service;

import com.skravetz.cn1grupo9.config.RabbitMQConfig;
import com.skravetz.cn1grupo9.dto.SaleDTO;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesService {

  private final AmqpTemplate amqpTemplate;

  public SaleDTO processSaleTransaction(SaleDTO saleDTO) {
    saleDTO.setSaleDate(LocalDateTime.now());
    if (saleDTO.getTransactionId() == null) {
      saleDTO.setTransactionId(UUID.randomUUID().toString());
    }

    if (saleDTO.getTotalAmount() == null
        && saleDTO.getPrice() != null
        && saleDTO.getQuantity() != null) {
      saleDTO.setTotalAmount(
          saleDTO.getPrice().multiply(java.math.BigDecimal.valueOf(saleDTO.getQuantity())));
    }

    log.info("Processing sale transaction: {}", saleDTO.getTransactionId());

    amqpTemplate.convertAndSend(
        RabbitMQConfig.SALES_EXCHANGE, RabbitMQConfig.SALES_ROUTING_KEY, saleDTO);

    log.info("Sale transaction sent to queue: {}", saleDTO.getTransactionId());

    return saleDTO;
  }
}
