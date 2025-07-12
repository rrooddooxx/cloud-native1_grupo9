package com.grupo9.inventorymscn1grupo9.inventory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "INVENTORY")
public class InventoryEntity {
  @Id
  @OneToOne
  @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
  private ProductEntity product;


  @Column(name = "STOCK_QUANTITY")
  private Integer stockQuantity;
}
