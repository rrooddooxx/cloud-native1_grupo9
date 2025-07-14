package com.grupo9.inventorymscn1grupo9.inventory;

import com.grupo9.inventorymscn1grupo9.product.Product;
import com.grupo9.inventorymscn1grupo9.product.ProductRepository;
import com.grupo9.inventorymscn1grupo9.sales.SaleEvent;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

  private final InventoryRepository inventoryRepository;
  private final ProductRepository productRepository;

  public void checkNewSaleForInventory(Optional<SaleEvent> saleEvent) {
    if (saleEvent.isEmpty()) {
      log.error("Sale Event is empty");
      return;
    }

    updateInventory(saleEvent.get());
  }

  private Optional<Product> checkProductOnSale(Integer productId) {
    return productRepository.findById(Long.valueOf(productId)).map(Product::fromEntity);
  }

  @Transactional
  public void updateInventory(SaleEvent sale) {
    var foundProduct = checkProductOnSale(sale.getProductId());
    if (foundProduct.isEmpty()) {
      log.error("Product not found");
      return;
    }

    Optional<InventoryEntity> currentInventory = inventoryRepository.findById(sale.getProductId());
    if (currentInventory.isEmpty()) {
      InventoryEntity inventoryEntity = new InventoryEntity();
      inventoryEntity.setProductId(sale.getProductId());
      inventoryEntity.setStockQuantity(0);
      inventoryRepository.save(inventoryEntity);
      return;
    }

    InventoryEntity inventoryEntity = currentInventory.get();
    inventoryEntity.setStockQuantity(1);

    log.info("Inventory updated");

    // 4. publish message to "stock" kafka topic
  }
}
