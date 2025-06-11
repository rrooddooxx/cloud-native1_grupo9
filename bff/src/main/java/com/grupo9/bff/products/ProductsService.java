package com.grupo9.bff.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${products.service.base-url:http://localhost:8080}")
    private String productsServiceBaseUrl;

    public List<Product> getAllProducts() {
        try {
            String url = productsServiceBaseUrl + "/api/products";
            log.debug("Calling products service: {}", url);
            
            String response = restTemplate.getForObject(url, String.class);
            List<Map<String, Object>> rawProducts = objectMapper.readValue(response, new TypeReference<List<Map<String, Object>>>() {});
            
            return rawProducts.stream()
                    .map(this::mapToProduct)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching products from external service", e);
            throw new RuntimeException("Failed to fetch products", e);
        }
    }

    public Optional<Product> getProductById(Long id) {
        try {
            String url = productsServiceBaseUrl + "/api/products/" + id;
            log.debug("Calling products service: {}", url);
            
            String response = restTemplate.getForObject(url, String.class);
            Map<String, Object> rawProduct = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});
            
            return Optional.of(mapToProduct(rawProduct));
        } catch (Exception e) {
            log.error("Error fetching product {} from external service", id, e);
            return Optional.empty();
        }
    }

    private Product mapToProduct(Map<String, Object> rawProduct) {
        try {
            Long id = getLongValue(rawProduct.get("id"));
            String title = getStringValue(rawProduct.get("title"));
            String description = getStringValue(rawProduct.get("description"));
            String imageUrl = getStringValue(rawProduct.get("imageUrl"));
            BigDecimal price = getBigDecimalValue(rawProduct.get("price"));
            String ownerId = getStringValue(rawProduct.get("ownerId"));
            Integer discount = getIntegerValue(rawProduct.get("discount"));
            LocalDate endsAt = getLocalDateValue(rawProduct.get("endsAt"));

            validateProduct(id, title, price);

            return Product.builder()
                    .id(id)
                    .title(title)
                    .description(description)
                    .imageUrl(imageUrl)
                    .price(price)
                    .ownerId(ownerId)
                    .discount(discount)
                    .endsAt(endsAt)
                    .build();
        } catch (Exception e) {
            log.error("Error mapping product: {}", rawProduct, e);
            throw new RuntimeException("Failed to map product", e);
        }
    }

    private void validateProduct(Long id, String title, BigDecimal price) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be positive");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Product title cannot be empty");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price must be non-negative");
        }
    }

    private Long getLongValue(Object value) {
        if (value == null) return null;
        if (value instanceof Number number) {
            return number.longValue();
        }
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String getStringValue(Object value) {
        return value != null ? value.toString() : null;
    }

    private BigDecimal getBigDecimalValue(Object value) {
        if (value == null) return null;
        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        try {
            return new BigDecimal(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer getIntegerValue(Object value) {
        if (value == null) return null;
        if (value instanceof Number number) {
            return number.intValue();
        }
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private LocalDate getLocalDateValue(Object value) {
        if (value == null) return null;
        try {
            return LocalDate.parse(value.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            log.warn("Failed to parse date: {}", value);
            return null;
        }
    }
}