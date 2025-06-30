package com.grupo9.bff.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Long productId;
    private String productTitle;
    private BigDecimal price;
    private Integer quantity;
    private String customerId;
    private String customerEmail;
}