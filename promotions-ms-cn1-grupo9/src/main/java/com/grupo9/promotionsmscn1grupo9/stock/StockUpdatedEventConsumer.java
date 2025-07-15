package com.grupo9.promotionsmscn1grupo9.stock;

import com.grupo9.promotionsmscn1grupo9.config.KafkaConsumerConstants;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StockUpdatedEventConsumer {

  private final CircularFifoQueue<StockUpdatedEvent> queue =
      new CircularFifoQueue<>(KafkaConsumerConstants.READ_QUEUE_LENGTH);

  @KafkaListener(
      topics = "${queues.stock}",
      containerFactory = "stockUpdatedEventKafkaListenerContainerFactory")
  private void listenStockUpdatedEvent(StockUpdatedEvent stockUpdatedEvent) {
    try {
      log.info("StockUpdatedEvent received: {}", stockUpdatedEvent);
      storeInQueue(stockUpdatedEvent);
    } catch (Exception e) {
      log.error("StockUpdatedEvent failed: {}", stockUpdatedEvent, e);
    }
  }

  private void storeInQueue(StockUpdatedEvent stockUpdatedEvent) {
    synchronized (queue) {
      queue.add(stockUpdatedEvent);
    }
  }

  public List<StockUpdatedEvent> getLastTenEvents() {
    synchronized (queue) {
      return new ArrayList<>(queue);
    }
  }
}
