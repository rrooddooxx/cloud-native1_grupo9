package com.grupo9.inventorymscn1grupo9.inventory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVENTORY", schema = "CN1G9")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryEntity {
  @Id
  @Column(name = "PRODUCT_ID")
  private Integer productId;

  @Column(name = "STOCK_QUANTITY")
  private Integer stockQuantity;

 
}
