package com.grupo9.salesms.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
  private Long id;
  private String title;
  private String description;
  private String imageUrl;
  private BigDecimal price;
  private String ownerId;
  private Integer discount;
  private LocalDateTime endsAt;
  private BigDecimal promotionPrice;

  public static Product fromEntity(ProductEntity entity) {
    if (entity == null) {
      return null;
    }

    return Product.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .description(entity.getDescription())
        .imageUrl(entity.getImageUrl())
        .price(entity.getPrice())
        .ownerId(entity.getOwnerId())
        .discount(entity.getDiscount())
        .endsAt(entity.getEndsAt())
        .promotionPrice(entity.getPromotionPrice())
        .build();
  }
}
