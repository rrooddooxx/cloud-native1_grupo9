package com.skravetz.cn1grupo9.dto;

import com.skravetz.cn1grupo9.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProductDTO {

    private Integer id;
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private String ownerId;
    private Integer discount;
    private LocalDate endsAt;

    public ProductDTO(Integer id, String title, String description, String imageUrl,
                      BigDecimal price, String ownerId, Integer discount, LocalDate endsAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.ownerId = ownerId;
        this.discount = discount == null ? 0 : discount;
        this.endsAt = endsAt;
    }
}