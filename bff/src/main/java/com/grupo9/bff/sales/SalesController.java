package com.grupo9.bff.sales;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/sales")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class SalesController {

    private final SalesService salesService;

    @PostMapping("/transaction")
    public ResponseEntity<Void> createTransaction(@RequestBody SaleDTO saleDTO) {
        try {
            log.info("Creating sales transaction for product: {}", saleDTO.getProductId());
            salesService.createSalesTransaction(saleDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error creating sales transaction", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}