package com.grupo9.promotionsmscn1grupo9.promotions;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record Promotion(
    String promotionId,
    Integer productId,
    String status,
    BigDecimal price,
    BigDecimal percentageApplied) {

  public PromotionsEntity toEntity() {
    return PromotionsEntity.builder()
        .promotionId(this.promotionId)
        .productId(this.productId)
        .status(this.status)
        .promotionPrice(this.price)
        .percentageApplied(this.percentageApplied)
        .build();
  }
}
