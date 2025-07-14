package com.grupo9.salesms.sales;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.Data;

@Entity
@Table(name = "SALES")
@Data
public class SalesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @Column(name = "CREATED_AT")
  ZonedDateTime createdAt;

  @Column(name = "CUSTOMER_EMAIL")
  String customerEmail;

  @Column(name = "CUSTOMER_ID")
  String customerId;

  @Column(name = "PRICE")
  Integer price;

  @Column(name = "PRODUCT_ID")
  Integer productId;

  @Column(name = "PRODUCT_TITLE")
  String productTitle;

  @Column(name = "QUANTITY")
  Integer quantity;

  @Column(name = "SALE_DATE")
  ZonedDateTime saleDate;

  @Column(name = "TOTAL_AMOUNT")
  Integer totalAmount;

  @Column(name = "TRANSACTION_ID")
  String transactionId;

  public Sale toDomain() {
    return Sale.builder()
        .customerEmail(this.getCustomerEmail())
        .customerId(this.getCustomerId())
        .price(this.getPrice())
        .productId(this.getProductId())
        .productTitle(this.getProductTitle())
        .quantity(this.getQuantity())
        .transactionId(this.getTransactionId())
        .totalAmount(this.getTotalAmount())
        .createdAt(this.getCreatedAt())
        .build();
  }
}
