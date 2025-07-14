package com.grupo9.inventorymscn1grupo9.inventory;

import com.grupo9.inventorymscn1grupo9.product.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVENTORY")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryEntity {
  @Id
  @Column(name = "PRODUCT_ID")
  private Integer productId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "PRODUCT_ID")
  private ProductEntity productEntity;

  @Column(name = "STOCK_QUANTITY")
  private Integer stockQuantity;
}
