package com.skravetz.cn1grupo9.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Integer productId;
    private String productTitle;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalAmount;
    private String customerId;
    private String customerEmail;
    @JsonAlias("createdAt")
    private LocalDateTime saleDate;
    private String transactionId;
}