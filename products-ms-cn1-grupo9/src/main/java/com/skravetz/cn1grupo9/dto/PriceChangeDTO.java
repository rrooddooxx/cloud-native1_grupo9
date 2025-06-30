package com.skravetz.cn1grupo9.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceChangeDTO {
    private Integer productId;
    private String productTitle;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    private String reason;
    private String updatedBy;
    private LocalDateTime changeDate;
}