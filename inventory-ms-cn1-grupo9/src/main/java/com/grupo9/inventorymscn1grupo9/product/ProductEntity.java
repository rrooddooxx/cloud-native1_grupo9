package com.grupo9.inventorymscn1grupo9.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCTS", schema = "CN1G9")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Long id;

  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "DESCRIPTION", length = 1000)
  private String description;

  @Column(name = "IMAGE_URL", length = 1000)
  private String imageUrl;

  @Column(name = "PRICE", precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "OWNER_ID", length = 50)
  private String ownerId;

  @Column(name = "DISCOUNT", precision = 3)
  private Integer discount;

  @Column(name = "ENDS_AT")
  private LocalDateTime endsAt;

  @Column(name = "PROMOTION_PRICE", precision = 10, scale = 2)
  private BigDecimal promotionPrice;

  public static ProductEntity fromDomain(Product domain) {

    return ProductEntity.builder()
        .id(domain.getId())
        .title(domain.getTitle())
        .description(domain.getDescription())
        .imageUrl(domain.getImageUrl())
        .price(domain.getPrice())
        .ownerId(domain.getOwnerId())
        .discount(domain.getDiscount())
        .endsAt(domain.getEndsAt())
        .promotionPrice(domain.getPromotionPrice())
        .build();
  }
}
