package com.grupo9.salesms.sales;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SalesController {

  private final SalesService salesService;

  @PostMapping("/create")
  public ResponseEntity<Map<String, String>> createSale(@RequestBody CreateSaleRequestDto dto) {
    try {
      salesService.createNewSale(dto);

      return ResponseEntity.ok().body(new HashMap<>());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new HashMap<>(Map.of("message", e.getMessage())));
    }
  }
}
