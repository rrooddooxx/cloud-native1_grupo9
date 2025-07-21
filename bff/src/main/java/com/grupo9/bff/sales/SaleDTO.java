package com.grupo9.bff.sales;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
  private String customerEmail;
  private String customerId;
  private Long productId;
  private BigDecimal price;
  private Integer quantity;
}
