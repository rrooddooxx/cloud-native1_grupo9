package com.grupo9.bff.promotions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {
    private Integer productId;
    private Object promotion;
    private String status;
    private String message;
}