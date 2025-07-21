package com.grupo9.inventorymscn1grupo9.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Integer> {
  @Modifying
  @Query(
      "UPDATE InventoryEntity i SET i.stockQuantity = i.stockQuantity - :quantity WHERE i.productId = :productId AND i.stockQuantity >= :quantity")
  int decrementStockAtomic(
      @Param("productId") Integer productId, @Param("quantity") Integer quantity);

  @Modifying
  @Query(
      "UPDATE InventoryEntity i SET i.stockQuantity = 0 WHERE i.productId = :productId AND i.stockQuantity < :quantity")
  int setStockToZeroIfInsufficient(
      @Param("productId") Integer productId, @Param("quantity") Integer quantity);
}
