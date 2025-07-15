package com.grupo9.bff.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSaleRequestDto {
    private String customerEmail;
    private String customerId;
    private Integer price;
    private Integer productId;
    private Integer quantity;
}