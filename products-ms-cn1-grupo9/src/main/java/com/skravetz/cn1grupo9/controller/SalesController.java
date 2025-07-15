package com.skravetz.cn1grupo9.controller;

import com.skravetz.cn1grupo9.dto.SaleDTO;
import com.skravetz.cn1grupo9.service.SalesService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@Slf4j
public class SalesController {

  private final SalesService salesService;

  @PostMapping("/transaction")
  public ResponseEntity<Map<String, String>> saleTransaction(@RequestBody SaleDTO saleDTO) {
    try {
      SaleDTO transaction = salesService.processSaleTransaction(saleDTO);
      Map<String, String> response = Map.of("transactionId", transaction.getTransactionId());
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      Map<String, String> response = Map.of("errorMessage", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }
}
