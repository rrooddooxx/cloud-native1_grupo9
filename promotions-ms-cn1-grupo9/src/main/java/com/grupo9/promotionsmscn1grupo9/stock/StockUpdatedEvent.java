package com.grupo9.promotionsmscn1grupo9.stock;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockUpdatedEvent {
  private Integer productId;
  private Integer quantity;
}
