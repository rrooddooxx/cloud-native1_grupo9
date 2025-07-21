package com.grupo9.bff.sales;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.grupo9.bff.config.RabbitMQConfig;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesService {

    private final RabbitTemplate rabbitTemplate;

    public void createSalesTransaction(SaleDTO saleDTO) {
        try {
            log.debug("Sending sales transaction message to RabbitMQ");
            
            CreateSaleRequestDto salesRequest = CreateSaleRequestDto.builder()
                .customerEmail(saleDTO.getCustomerEmail())
                .customerId(saleDTO.getCustomerId())
                .price(saleDTO.getPrice() != null ? saleDTO.getPrice().intValue() : null)
                .productId(saleDTO.getProductId() != null ? saleDTO.getProductId().intValue() : null)
                .quantity(saleDTO.getQuantity())
                .build();
            
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.SALES_EXCHANGE, 
                RabbitMQConfig.SALES_ROUTING_KEY, 
                salesRequest
            );
            
            log.info("Sales transaction message sent successfully to RabbitMQ with routing key: {}", RabbitMQConfig.SALES_ROUTING_KEY);
        } catch (Exception e) {
            log.error("Error sending sales transaction message to RabbitMQ", e);
            throw new RuntimeException("Failed to send sales transaction message", e);
        }
    }
}