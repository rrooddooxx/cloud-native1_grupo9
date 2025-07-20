package com.grupo9.inventorymscn1grupo9.inventory;

import com.grupo9.inventorymscn1grupo9.product.Product;
import lombok.Data;

@Data
public class Inventory {
  private Integer productId;
  private Integer stockQuantity;
  private Product product;
}
