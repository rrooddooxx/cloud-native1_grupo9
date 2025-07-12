package com.grupo9.salesms.sales;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "SALES")
public class SalesEntity {

  @Id Integer id;

  @Column(name = "CREATED_AT")
  ZonedDateTime createdAt;

  @Column(name = "CUSTOMER_EMAIL")
  String customerEmail;

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
}
