package com.grupo9.bff.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final RestTemplate restTemplate;

    @Value("${inventory.service.base-url:http://localhost:8083}")
    private String inventoryServiceBaseUrl;

    public List<InventoryDTO> getAllInventory() {
        try {
            log.debug("Fetching all inventory data");
            return Arrays.asList(
                InventoryDTO.builder()
                    .productId(1)
                    .productTitle("Sample Product 1")
                    .stock(100)
                    .reserved(5)
                    .available(95)
                    .status("AVAILABLE")
                    .build(),
                InventoryDTO.builder()
                    .productId(2)
                    .productTitle("Sample Product 2")
                    .stock(50)
                    .reserved(10)
                    .available(40)
                    .status("LOW_STOCK")
                    .build()
            );
        } catch (Exception e) {
            log.error("Error fetching inventory data", e);
            throw new RuntimeException("Failed to fetch inventory data", e);
        }
    }

    public Optional<InventoryDTO> getInventoryByProductId(Integer productId) {
        try {
            log.debug("Fetching inventory for product: {}", productId);
            return getAllInventory().stream()
                .filter(inventory -> inventory.getProductId().equals(productId))
                .findFirst();
        } catch (Exception e) {
            log.error("Error fetching inventory for product {}", productId, e);
            return Optional.empty();
        }
    }
}