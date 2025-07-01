package com.skravetz.cn1grupo9.consumer;

import com.skravetz.cn1grupo9.config.RabbitMQConfig;
import com.skravetz.cn1grupo9.dto.SaleDTO;
import com.skravetz.cn1grupo9.entity.Sale;
import com.skravetz.cn1grupo9.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SalesConsumer {

  private final SaleRepository saleRepository;

  @RabbitListener(queues = RabbitMQConfig.SALES_QUEUE)
  public void processSaleMessage(SaleDTO saleDTO) {
    try {
      log.info("Received sale message for transaction: {}", saleDTO.getTransactionId());

      if (saleRepository.findByTransactionId(saleDTO.getTransactionId()).isPresent()) {
        log.warn(
            "Sale with transaction ID {} already exists, skipping", saleDTO.getTransactionId());
        return;
      }

      Sale sale = new Sale();
      sale.setProductId(saleDTO.getProductId());
      sale.setProductTitle(saleDTO.getProductTitle());
      sale.setPrice(saleDTO.getPrice());
      sale.setQuantity(saleDTO.getQuantity());
      sale.setTotalAmount(saleDTO.getTotalAmount());
      sale.setCustomerId(saleDTO.getCustomerId());
      sale.setCustomerEmail(saleDTO.getCustomerEmail());
      sale.setSaleDate(saleDTO.getSaleDate());
      sale.setTransactionId(saleDTO.getTransactionId());

      Sale savedSale = saleRepository.save(sale);

      log.info(
          "Sale saved successfully with ID: {} for transaction: {}",
          savedSale.getId(),
          savedSale.getTransactionId());

    } catch (Exception e) {
      log.error("Error processing sale message for transaction: {}", saleDTO.getTransactionId(), e);
      throw new RuntimeException("Failed to process sale message", e);
    }
  }
}
