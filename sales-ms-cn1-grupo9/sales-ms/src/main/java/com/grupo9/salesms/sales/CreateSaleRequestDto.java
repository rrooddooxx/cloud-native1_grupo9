package com.grupo9.salesms.sales;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class CreateSaleRequestDto {
  private String customerEmail;
  private String customerId;
  private Integer price;
  private String productId;
  private Integer quantity;

  public Sale toDomain(
      CreateSaleRequestDto dto,
      ZonedDateTime creationDate,
      Integer totalAmount,
      String transactionId) {
    return Sale.builder()
        .createdAt(creationDate)
        .customerEmail(dto.customerEmail)
        .customerId(dto.customerId)
        .price(dto.price)
        .productId(dto.productId)
        .quantity(dto.quantity)
        .totalAmount(totalAmount)
        .transactionId(transactionId)
        .build();
  }
}
