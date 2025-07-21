package com.grupo9.inventorymscn1grupo9.inventory;

import com.grupo9.inventorymscn1grupo9.product.Product;
import com.grupo9.inventorymscn1grupo9.product.ProductRepository;
import com.grupo9.inventorymscn1grupo9.sales.SaleEvent;
import com.grupo9.inventorymscn1grupo9.stock.StockEvent;
import com.grupo9.inventorymscn1grupo9.stock.StockMessagingProducer;
import jakarta.transaction.Transactional;
import java.util.List;
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
  private final StockMessagingProducer stockMessagingProducer;

  @Transactional
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

  public List<InventoryEntity> getAllInventory() {
    return inventoryRepository.findAll();
  }

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
      stockMessagingProducer.sendKafkaStockUpdatedMessage(
          StockEvent.builder().productId(inventoryEntity.getProductId()).quantity(0).build());
      return;
    }

    InventoryEntity inventoryEntity = currentInventory.get();
    Integer newQuantity = inventoryEntity.getStockQuantity() + sale.getQuantity();
    inventoryEntity.setStockQuantity(newQuantity);
    stockMessagingProducer.sendKafkaStockUpdatedMessage(
        StockEvent.builder()
            .productId(inventoryEntity.getProductId())
            .quantity(newQuantity)
            .build());
    log.info("Inventory updated");
  }
}
