package com.grupo9.promotionsmscn1grupo9.promotions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROMOTIONS")
public class PromotionEntity {
  @Id
  @Column(name = "PROMOTION_ID", nullable = false, length = 40)
  private String promotionId;

  @Column(name = "STATUS", nullable = false, length = 100)
  private String status;

  @Column(name = "NEW_STOCK_PRICE", nullable = false, precision = 10, scale = 2)
  private BigDecimal newStockPrice;

  @Column(name = "PERCENTAGE_APPLIED", nullable = false, precision = 5, scale = 4)
  private BigDecimal percentageApplied;
}
