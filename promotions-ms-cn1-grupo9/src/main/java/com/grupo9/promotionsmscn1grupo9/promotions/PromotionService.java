package com.grupo9.promotionsmscn1grupo9.promotions;

import com.grupo9.promotionsmscn1grupo9.sales.SaleFinishedEvent;
import com.grupo9.promotionsmscn1grupo9.sales.SaleFinishedEventConsumer;
import com.grupo9.promotionsmscn1grupo9.stock.StockUpdatedEvent;
import com.grupo9.promotionsmscn1grupo9.stock.StockUpdatedEventConsumer;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {

  private final BigDecimal UPPER_DISCOUNT_PERCENTAGE = BigDecimal.valueOf(0.05);
  private final BigDecimal LOWER_DISCOUNT_PERCENTAGE = BigDecimal.valueOf(0.25);
  private final String RESULT_MAP = "result";

  private final PromotionsRepository promotionsRepository;
  private final StockUpdatedEventConsumer stockUpdatedEventConsumer;
  private final SaleFinishedEventConsumer saleFinishedEventConsumer;

  public Promotion generateNewPromotion(Integer productId) {
    var strategy = evaluateDataForPromotion(productId);
    var tempPromo = Promotion.builder();
    tempPromo.promotionId(UUID.randomUUID().toString());
    tempPromo.productId(productId);

    if (strategy.get(RESULT_MAP).strategy.equals(PromotionStrategy.LOWER_COST)) {
      tempPromo.percentageApplied(LOWER_DISCOUNT_PERCENTAGE);
      tempPromo.price(
          BigDecimal.valueOf(strategy.get(RESULT_MAP).price)
              .subtract(
                  BigDecimal.valueOf(strategy.get(RESULT_MAP).price)
                      .multiply(LOWER_DISCOUNT_PERCENTAGE)));
    } else {
      tempPromo.percentageApplied(UPPER_DISCOUNT_PERCENTAGE);
      tempPromo.price(
          BigDecimal.valueOf(strategy.get(RESULT_MAP).price)
              .subtract(
                  BigDecimal.valueOf(strategy.get(RESULT_MAP).price)
                      .multiply(UPPER_DISCOUNT_PERCENTAGE)));
    }
    tempPromo.status("ACTIVE");
    Promotion promo = tempPromo.build();

    registerNewPromotion(promo);
    return promo;
  }

  private Map<String, PromotionStrategyUnit> evaluateDataForPromotion(Integer productId) {
    var stockEvents = stockUpdatedEventConsumer.getLastTenEvents();
    var saleEvents = saleFinishedEventConsumer.getLastTenEvents();
    log.info("Stock events {}", stockEvents);
    log.info("Sale events: {}", saleEvents);

    var stockAvgByProductList =
        stockEvents.stream().filter(event -> event.getProductId().equals(productId)).toList();

    var stockCount = stockAvgByProductList.size();
    var stockSum = stockAvgByProductList.stream().mapToInt(StockUpdatedEvent::getQuantity).sum();
    var stockAvgBySaleList =
        Integer.divideUnsigned(stockCount > 0 ? stockCount : 1, stockSum > 0 ? stockSum : 1);
    var saleCountByProductId =
        saleEvents.stream().filter(event -> event.getProductId().equals(productId)).count();
    var lastProductPrice =
        saleEvents.stream()
            .filter(event -> event.getProductId().equals(productId))
            .findFirst()
            .map(SaleFinishedEvent::getPrice);

    if (lastProductPrice.isEmpty()) {
      throw new RuntimeException("No price for product " + productId + " found");
    }

    if (stockAvgBySaleList < 5 && saleCountByProductId > 5) {
      return Map.of(
          RESULT_MAP,
          new PromotionStrategyUnit(PromotionStrategy.UPPER_COST, lastProductPrice.get()));
    } else if (stockAvgBySaleList >= 5 && saleCountByProductId < 5) {
      return Map.of(
          RESULT_MAP,
          new PromotionStrategyUnit(PromotionStrategy.LOWER_COST, lastProductPrice.get()));
    } else {
      return Map.of(
          RESULT_MAP,
          new PromotionStrategyUnit(PromotionStrategy.LOWER_COST, lastProductPrice.get()));
    }
  }

  private void registerNewPromotion(Promotion promotion) {
    PromotionsEntity promotionsEntity = promotion.toEntity();
    promotionsRepository.save(promotionsEntity);
  }

  @AllArgsConstructor
  private static final class PromotionStrategyUnit {
    PromotionStrategy strategy;
    Integer price;
  }
}
