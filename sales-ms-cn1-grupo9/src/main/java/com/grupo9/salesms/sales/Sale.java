package com.grupo9.salesms.sales;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Sale {
  private String customerEmail;
  private String customerId;
  private Integer price;
  private Integer productId;
  private String productTitle;
  private Integer quantity;
  private String transactionId;
  private Integer totalAmount;
  private ZonedDateTime createdAt;

  public SalesEntity toEntity(String productTitle) {
    SalesEntity entity = new SalesEntity();
    entity.setCustomerEmail(this.customerEmail);
    entity.setCustomerId(this.customerId);
    entity.setPrice(this.price);
    entity.setProductId(this.productId);
    entity.setProductTitle(productTitle);
    entity.setQuantity(this.quantity);
    entity.setTransactionId(this.transactionId);
    entity.setTotalAmount(this.totalAmount);
    entity.setCreatedAt(this.createdAt);
    entity.setSaleDate(this.createdAt);
    return entity;
  }
}
