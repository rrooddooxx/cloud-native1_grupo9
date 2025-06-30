package com.grupo9.bff.pricechange;

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
public class PriceChangeService {

    private final RestTemplate restTemplate;

    @Value("${products.service.base-url:http://localhost:8080}")
    private String productsServiceBaseUrl;

    public void updatePrice(PriceChangeDTO priceChangeDTO) {
        try {
            String url = productsServiceBaseUrl + "/api/price-changes/update";
            log.debug("Calling products service for price change: {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PriceChangeDTO> request = new HttpEntity<>(priceChangeDTO, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            log.info("Price change updated successfully with status: {}", response.getStatusCode());
        } catch (Exception e) {
            log.error("Error updating price change", e);
            throw new RuntimeException("Failed to update price change", e);
        }
    }
}