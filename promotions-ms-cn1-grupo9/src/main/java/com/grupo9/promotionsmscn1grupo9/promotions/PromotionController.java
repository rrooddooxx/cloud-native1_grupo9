package com.grupo9.promotionsmscn1grupo9.promotions;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/promotion")
@RequiredArgsConstructor
public class PromotionController {

  private final PromotionService promotionService;

  @GetMapping("/create/{productId}")
  public ResponseEntity<Map<String, ?>> createPromotion(
      @PathVariable(value = "productId", required = true) Integer productId) {
    try {
      var generatedPromotion = promotionService.generateNewPromotion(productId);
      return ResponseEntity.ok(Map.of("success", generatedPromotion));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("error", e.getMessage()));
    }
  }
}
