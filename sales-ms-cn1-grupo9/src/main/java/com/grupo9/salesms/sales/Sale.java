package com.grupo9.salesms.sales;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class Sale {
  private String customerEmail;
  private String customerId;
  private Integer price;
  private String productId;
  private String productTitle;
  private Integer quantity;
  private String transactionId;
  private Integer totalAmount;
  private ZonedDateTime createdAt;
}
