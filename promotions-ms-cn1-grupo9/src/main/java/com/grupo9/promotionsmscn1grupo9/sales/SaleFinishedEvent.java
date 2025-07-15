package com.grupo9.promotionsmscn1grupo9.sales;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class SaleFinishedEvent {
  private String customerEmail;
  private String customerId;
  private Integer price;
  private Integer productId;
  private String productTitle;
  private Integer quantity;
  private String transactionId;
  private Integer totalAmount;
  private ZonedDateTime createdAt;
}
