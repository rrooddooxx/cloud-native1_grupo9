package com.grupo9.bff.promotions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${promotions.service.base-url:http://localhost:8082}")
    private String promotionsServiceBaseUrl;

    public PromotionDTO createPromotion(Integer productId) {
        try {
            String url = promotionsServiceBaseUrl + "/api/v1/promotion/create/" + productId;
            log.debug("Calling promotions service: {}", url);
            
            String response = restTemplate.getForObject(url, String.class);
            Map<String, Object> rawResponse = objectMapper.readValue(response, new TypeReference<>() {});
            
            return mapToPromotionDTO(productId, rawResponse);
        } catch (Exception e) {
            log.error("Error creating promotion for product {}", productId, e);
            return new PromotionDTO(productId, null, "error", "Failed to create promotion: " + e.getMessage());
        }
    }

    private PromotionDTO mapToPromotionDTO(Integer productId, Map<String, Object> rawResponse) {
        if (rawResponse.containsKey("success")) {
            return new PromotionDTO(
                productId,
                rawResponse.get("success"),
                "success",
                "Promotion created successfully"
            );
        } else if (rawResponse.containsKey("error")) {
            return new PromotionDTO(
                productId,
                null,
                "error",
                rawResponse.get("error").toString()
            );
        } else {
            return new PromotionDTO(
                productId,
                null,
                "error",
                "Unknown response format"
            );
        }
    }
}