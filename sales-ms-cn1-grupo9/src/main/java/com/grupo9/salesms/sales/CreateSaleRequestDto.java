package com.grupo9.salesms.sales;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class CreateSaleRequestDto {
  private String customerEmail;
  private String customerId;
  private Integer price;
  private Integer productId;
  private Integer quantity;

  public Sale toDomain(String transactionId) {
    return Sale.builder()
        .createdAt(ZonedDateTime.now())
        .customerEmail(this.customerEmail)
        .customerId(this.customerId)
        .price(this.price)
        .productId(this.productId)
        .quantity(this.quantity)
        .totalAmount(this.quantity * this.price)
        .transactionId(transactionId)
        .build();
  }
}
