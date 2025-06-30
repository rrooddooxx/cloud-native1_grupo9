package com.grupo9.bff.pricechange;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/price-changes")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class PriceChangeController {

    private final PriceChangeService priceChangeService;

    @PostMapping("/update")
    public ResponseEntity<Void> updatePrice(@RequestBody PriceChangeDTO priceChangeDTO) {
        try {
            log.info("Updating price for product: {}", priceChangeDTO.getProductId());
            priceChangeService.updatePrice(priceChangeDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error updating price", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}