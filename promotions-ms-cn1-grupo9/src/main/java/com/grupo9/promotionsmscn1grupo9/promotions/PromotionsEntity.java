package com.grupo9.promotionsmscn1grupo9.promotions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROMOTIONS", schema = "CN1G9")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionsEntity {

  @Id
  @Column(name = "PROMOTION_ID")
  private String promotionId;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "PROMOTION_PRICE")
  private BigDecimal promotionPrice;

  @Column(name = "PERCENTAGE_APPLIED")
  private BigDecimal percentageApplied;

  @Column(name = "PRODUCT_ID")
  private Integer productId;

  public Promotion toDomain() {
    return Promotion.builder()
        .promotionId(this.promotionId)
        .productId(this.productId)
        .status(this.status)
        .percentageApplied(this.percentageApplied)
        .price(this.promotionPrice)
        .build();
  }
}
