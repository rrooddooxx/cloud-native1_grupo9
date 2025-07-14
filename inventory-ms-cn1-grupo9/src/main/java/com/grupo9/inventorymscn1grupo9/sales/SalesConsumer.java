package com.grupo9.inventorymscn1grupo9.sales;

import com.grupo9.inventorymscn1grupo9.inventory.InventoryService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SalesConsumer {

  private final InventoryService inventoryService;

  @KafkaListener(
      topics = "${queues-kafka.sale-finished-topic}",
      containerFactory = "saleFinishedKafkaListenerContainerFactory")
  public void listenSaleFinished(SaleEvent data) {
    try {
      log.info("Received data from stock topcic: {}", data);
      inventoryService.checkNewSaleForInventory(Optional.ofNullable(data));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
