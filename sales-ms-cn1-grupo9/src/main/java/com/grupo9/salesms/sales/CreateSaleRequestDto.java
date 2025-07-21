package com.grupo9.salesms.sales;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class CreateSaleRequestDto {
  private String customerEmail;
  private String customerId;
  private BigDecimal price;
  private Integer productId;
  private Integer quantity;

  public Sale toDomain(String transactionId) {
    return Sale.builder()
        .createdAt(ZonedDateTime.now())
        .customerEmail(this.customerEmail)
        .customerId(this.customerId)
        .price(this.price.intValueExact())
        .productId(this.productId)
        .quantity(this.quantity)
        .totalAmount(this.quantity * this.price.intValueExact())
        .transactionId(transactionId)
        .build();
  }
}
