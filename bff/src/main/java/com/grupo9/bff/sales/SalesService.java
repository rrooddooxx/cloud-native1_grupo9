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

    @Value("${products.service.base-url:http://localhost:8080}")
    private String productsServiceBaseUrl;

    public void createSalesTransaction(SaleDTO saleDTO) {
        try {
            String url = productsServiceBaseUrl + "/api/sales/transaction";
            log.debug("Calling products service for sales transaction: {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<SaleDTO> request = new HttpEntity<>(saleDTO, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            log.info("Sales transaction created successfully with status: {}", response.getStatusCode());
        } catch (Exception e) {
            log.error("Error creating sales transaction", e);
            throw new RuntimeException("Failed to create sales transaction", e);
        }
    }
}