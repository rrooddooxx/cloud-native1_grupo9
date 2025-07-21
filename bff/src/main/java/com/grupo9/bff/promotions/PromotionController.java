package com.grupo9.bff.promotions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private/promotions")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("/create/{productId}")
    public ResponseEntity<PromotionDTO> createPromotion(@PathVariable Integer productId) {
        try {
            log.info("Creating promotion for product: {}", productId);
            PromotionDTO result = promotionService.createPromotion(productId);
            
            if ("success".equals(result.getStatus())) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }
        } catch (Exception e) {
            log.error("Error creating promotion for product {}", productId, e);
            PromotionDTO errorResult = new PromotionDTO(
                productId, 
                null, 
                "error", 
                "Internal server error"
            );
            return ResponseEntity.internalServerError().body(errorResult);
        }
    }
}