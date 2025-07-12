package com.grupo9.salesms.sales;

import lombok.RequiredArgsConstructor;
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
  public void createSale(
          @RequestBody(required = true) CreateSaleRequestDto createSaleRequestDto
  ){
    salesService.createSale(createSaleRequestDto);
  }
}
