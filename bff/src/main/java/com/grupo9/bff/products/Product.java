package com.grupo9.bff.products;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private String ownerId;
    private Integer discount;
    private LocalDate endsAt;

    public BigDecimal getFinalPrice() {
        if (discount == null || discount == 0) {
            return price;
        }
        BigDecimal discountAmount = price.multiply(BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100)));
        return price.subtract(discountAmount);
    }
}