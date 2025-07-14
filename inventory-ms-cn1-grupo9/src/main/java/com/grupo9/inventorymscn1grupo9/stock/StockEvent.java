package com.grupo9.inventorymscn1grupo9.stock;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockEvent {
  private Integer productId;
  private Integer quantity;
}
