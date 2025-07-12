package com.grupo9.salesms.sales;

import org.springframework.stereotype.Service;

@Service
public class SalesService {
  public void createSale(CreateSaleRequestDto dto) {
    // 1. parse to domain object
    // 2. lookup product information
    // 3. register sale in DB
    // 4. emit event to publish the sale
  }
}
