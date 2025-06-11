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
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal originalPrice;
    private BigDecimal finalPrice;
    private String ownerId;
    private Integer discount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime discountEndsAt;
    private boolean hasDiscount;

    public static ProductDTO fromProduct(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .originalPrice(product.getPrice())
                .finalPrice(product.getFinalPrice())
                .ownerId(product.getOwnerId())
                .discount(product.getDiscount())
                .discountEndsAt(product.getEndsAt())
                .hasDiscount(product.getDiscount() != null && product.getDiscount() > 0)
                .build();
    }
}