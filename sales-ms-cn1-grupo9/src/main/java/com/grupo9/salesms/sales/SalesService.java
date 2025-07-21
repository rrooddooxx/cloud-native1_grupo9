package com.grupo9.salesms.sales;

import com.grupo9.salesms.product.Product;
import com.grupo9.salesms.product.ProductService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesService {

  private final ProductService productService;
  private final SalesRepository salesRepository;
  private final SalesMessagingProducer msgSender;

  public void createNewSale(CreateSaleRequestDto dto) {
    try {
      log.info("Creating new sale for {}", dto);
      Sale newSale = dto.toDomain(UUID.randomUUID().toString());
      ValidatedSale validSale = validateSale(newSale);
      if (validSale.status().equals(false)) {
        throw new RuntimeException("Sale not valid");
      }

      log.info("New sale created, Publishing...");
      publishSale(validSale.sale(), validSale.product());
    } catch (Exception e) {
      throw new RuntimeException("Error creating new sale: %s".formatted(e.getMessage()));
    }
  }

  private ValidatedSale validateSale(Sale sale) {
    log.info("Validating sale...");
    if (sale.getTotalAmount() == null || sale.getTransactionId() == null) {
      log.error("Sale not valid: Amount or transaction id is null");
      return ValidatedSale.builder().status(false).build();
    }
    log.info("Validating Sale {}", sale);
    log.info("Validating ID {}", Long.valueOf(sale.getProductId()));
    Optional<Product> product = productService.getProductById(Long.valueOf(sale.getProductId()));
    if (product.isEmpty()) {
      return ValidatedSale.builder().status(false).build();
    }

    sale.setProductTitle(product.get().getTitle());
    sale.setProductId(Math.toIntExact(product.get().getId()));
    log.info("Product from database: {}", product.get());
    log.info("Sale transformed: {}", sale);
    return ValidatedSale.builder().status(true).product(product.get()).sale(sale).build();
  }

  public void publishSale(Sale sale, Product product) {
    log.info("Processing sale transaction: {}", sale.getTransactionId());

    registerSaleInDatabase(sale, product);
    msgSender.sendKafkaSaleFinishedMessage(sale);

    log.info("Message sent to Queues: {}", sale);
    log.info(
        "Sale transaction sent to queues and registered in db, transaction id: {}",
        sale.getTransactionId());
  }

  public void registerSaleInDatabase(Sale sale, Product product) {
    try {
      log.info("Registering sale transaction in DB: {}", sale.getTransactionId());
      SalesEntity newSale = sale.toEntity(product.getTitle());
      salesRepository.save(newSale);
    } catch (Exception e) {
      log.error("Cannot register sale in DB: {}", e.getMessage());
      throw new RuntimeException("Cannot register sale in DB");
    }
  }
}
