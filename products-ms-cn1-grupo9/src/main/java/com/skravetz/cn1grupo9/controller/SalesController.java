package com.skravetz.cn1grupo9.controller;

import com.skravetz.cn1grupo9.config.RabbitMQConfig;
import com.skravetz.cn1grupo9.dto.SaleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@Slf4j
public class SalesController {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/transaction")
    public ResponseEntity<String> processSaleTransaction(@RequestBody SaleDTO saleDTO) {
        try {
            // Set transaction details
            saleDTO.setSaleDate(LocalDateTime.now());
            if (saleDTO.getTransactionId() == null) {
                saleDTO.setTransactionId(UUID.randomUUID().toString());
            }

            // Calculate total amount if not provided
            if (saleDTO.getTotalAmount() == null && saleDTO.getPrice() != null && saleDTO.getQuantity() != null) {
                saleDTO.setTotalAmount(saleDTO.getPrice().multiply(java.math.BigDecimal.valueOf(saleDTO.getQuantity())));
            }

            log.info("Processing sale transaction: {}", saleDTO.getTransactionId());

            // Send message to RabbitMQ
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.SALES_EXCHANGE,
                    RabbitMQConfig.SALES_ROUTING_KEY,
                    saleDTO
            );

            log.info("Sale transaction sent to queue: {}", saleDTO.getTransactionId());

            return ResponseEntity.ok("Sale transaction processed successfully. Transaction ID: " + saleDTO.getTransactionId());

        } catch (Exception e) {
            log.error("Error processing sale transaction", e);
            return ResponseEntity.internalServerError()
                    .body("Error processing sale transaction: " + e.getMessage());
        }
    }
}