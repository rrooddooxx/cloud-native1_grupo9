package com.grupo9.bff.pricechange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceChangeDTO {
    private Long productId;
    private String productTitle;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    private String reason;
    private String updatedBy;
}