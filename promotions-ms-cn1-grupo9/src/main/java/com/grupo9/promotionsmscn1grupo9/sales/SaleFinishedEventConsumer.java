package com.grupo9.promotionsmscn1grupo9.sales;

import com.grupo9.promotionsmscn1grupo9.config.KafkaConsumerConstants;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SaleFinishedEventConsumer {

  private final CircularFifoQueue<SaleFinishedEvent> queue =
      new CircularFifoQueue<>(KafkaConsumerConstants.READ_QUEUE_LENGTH);

  @KafkaListener(
      topics = "${queues.sales}",
      containerFactory = "saleFinishedEventKafkaListenerContainerFactory")
  private void listenEvent(SaleFinishedEvent saleFinishedEvent) {
    try {
      log.info("Received SaleFinishedEvent {}", saleFinishedEvent);
      storeInQueue(saleFinishedEvent);
    } catch (Exception e) {
      log.error("Error processing SaleFinishedEvent {}", saleFinishedEvent, e);
    }
  }

  public void storeInQueue(SaleFinishedEvent saleFinishedEvent) {
    synchronized (queue) {
      queue.add(saleFinishedEvent);
    }
  }

  public List<SaleFinishedEvent> getLastTenEvents() {
    return new ArrayList<>(queue);
  }
}
