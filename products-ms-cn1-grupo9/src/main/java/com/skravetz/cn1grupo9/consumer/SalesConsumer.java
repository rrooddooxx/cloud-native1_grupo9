package com.skravetz.cn1grupo9.consumer;

import com.skravetz.cn1grupo9.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SalesConsumer {

  private final SaleRepository saleRepository;
/*
  @RabbitListener(queues = RabbitMQConfig.SALES_QUEUE)
  public void processSaleMessage(String saleMessage) {
    try {
      log.info("Received sale message: {}", saleMessage);

      SaleDTO saleDTO = parseStringToSaleDTO(saleMessage);

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
      log.error("Error processing sale message: {}", saleMessage, e);
      throw new RuntimeException("Failed to process sale message", e);
    }
  }

  private SaleDTO parseStringToSaleDTO(String saleMessage) {
    try {
      // Parse the toString() format: Sale(customerEmail=john.doe@example.com, customerId=CUST-999, price=3555, productId=4, productTitle=iPad Pro, quantity=4, transactionId=52aa2e16-e81e-4d17-b773-887bf298f4a5, totalAmount=14220, createdAt=2025-07-15T03:24:15.042561714Z[Etc/UTC])
      String content = saleMessage.substring(saleMessage.indexOf('(') + 1, saleMessage.lastIndexOf(')'));
      String[] pairs = content.split(", ");
      
      SaleDTO saleDTO = new SaleDTO();
      
      for (String pair : pairs) {
        String[] keyValue = pair.split("=", 2);
        if (keyValue.length == 2) {
          String key = keyValue[0].trim();
          String value = keyValue[1].trim();
          
          switch (key) {
            case "customerEmail":
              saleDTO.setCustomerEmail(value);
              break;
            case "customerId":
              saleDTO.setCustomerId(value);
              break;
            case "price":
              saleDTO.setPrice(new java.math.BigDecimal(value));
              break;
            case "productId":
              saleDTO.setProductId(Integer.parseInt(value));
              break;
            case "productTitle":
              saleDTO.setProductTitle(value);
              break;
            case "quantity":
              saleDTO.setQuantity(Integer.parseInt(value));
              break;
            case "transactionId":
              saleDTO.setTransactionId(value);
              break;
            case "totalAmount":
              saleDTO.setTotalAmount(new java.math.BigDecimal(value));
              break;
            case "createdAt":
              saleDTO.setSaleDate(java.time.LocalDateTime.parse(value.substring(0, value.indexOf('['))));
              break;
          }
        }
      }
      
      return saleDTO;
    } catch (Exception e) {
      log.error("Error parsing sale message string: {}", saleMessage, e);
      throw new RuntimeException("Failed to parse sale message", e);
    }
  }*/
}
