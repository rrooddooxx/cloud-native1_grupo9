package com.grupo9.bff.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/inventory")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventory() {
        try {
            log.info("Fetching all inventory data");
            List<InventoryDTO> inventory = inventoryService.getAllInventory();
            return ResponseEntity.ok(inventory);
        } catch (Exception e) {
            log.error("Error retrieving inventory", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProductId(@PathVariable Integer productId) {
        try {
            log.info("Fetching inventory for product: {}", productId);
            return inventoryService.getInventoryByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error retrieving inventory for product {}", productId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}