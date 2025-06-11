package com.grupo9.bff.products;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endsAt;

    public BigDecimal getFinalPrice() {
        if (discount == null || discount == 0) {
            return price;
        }
        BigDecimal discountAmount = price.multiply(BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100)));
        return price.subtract(discountAmount);
    }
}