package com.grupo9.promotionsmscn1grupo9.promotions;

import com.grupo9.promotionsmscn1grupo9.sales.SaleFinishedEventConsumer;
import com.grupo9.promotionsmscn1grupo9.stock.StockUpdatedEventConsumer;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {

  private final PromotionsRepository promotionsRepository;
  private final StockUpdatedEventConsumer stockUpdatedEventConsumer;
  private final SaleFinishedEventConsumer saleFinishedEventConsumer;

  public Promotion generateNewPromotion(Integer productId) {
    evaluateDataForPromotion();
    var promo = new Promotion("abc", productId, "success", BigDecimal.ONE, BigDecimal.TWO);
    registerNewPromotion(promo);
    return promo;
  }

  private void evaluateDataForPromotion() {
    var stockEvents = stockUpdatedEventConsumer.getLastTenEvents();
    var saleEvents = saleFinishedEventConsumer.getLastTenEvents();
    log.info("Stock events {}", stockEvents);
    log.info("Sale events: {}", saleEvents);
  }

  private void registerNewPromotion(Promotion promotion) {
    PromotionsEntity promotionsEntity = promotion.toEntity();
    promotionsRepository.save(promotionsEntity);
  }
}
