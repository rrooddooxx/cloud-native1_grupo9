package com.grupo9.bff.sales;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesService {

    private final RestTemplate restTemplate;

    @Value("${sales.service.base-url:http://localhost:8081}")
    private String salesServiceBaseUrl;

    public void createSalesTransaction(SaleDTO saleDTO) {
        try {
            String url = salesServiceBaseUrl + "/api/v1/sales/create";
            log.debug("Calling sales service for sales transaction: {}", url);
            
            CreateSaleRequestDto salesRequest = CreateSaleRequestDto.builder()
                .customerEmail(saleDTO.getCustomerEmail())
                .customerId(saleDTO.getCustomerId())
                .price(saleDTO.getPrice() != null ? saleDTO.getPrice().intValue() : null)
                .productId(saleDTO.getProductId() != null ? saleDTO.getProductId().intValue() : null)
                .quantity(saleDTO.getQuantity())
                .build();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CreateSaleRequestDto> request = new HttpEntity<>(salesRequest, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            log.info("Sales transaction created successfully with status: {}", response.getStatusCode());
        } catch (Exception e) {
            log.error("Error creating sales transaction", e);
            throw new RuntimeException("Failed to create sales transaction", e);
        }
    }
}