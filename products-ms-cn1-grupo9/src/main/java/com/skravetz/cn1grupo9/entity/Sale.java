package com.skravetz.cn1grupo9.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "SALES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Integer productId;

    @Column(name = "PRODUCT_TITLE", nullable = false, length = 255)
    private String productTitle;

    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "TOTAL_AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "CUSTOMER_ID", nullable = false, length = 100)
    private String customerId;

    @Column(name = "CUSTOMER_EMAIL", length = 255)
    private String customerEmail;

    @Column(name = "SALE_DATE", nullable = false)
    private LocalDateTime saleDate;

    @Column(name = "TRANSACTION_ID", nullable = false, length = 100, unique = true)
    private String transactionId;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}