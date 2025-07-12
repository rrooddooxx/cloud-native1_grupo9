package com.grupo9.promotionsmscn1grupo9.promotions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionsService {

  private final PromotionsRepository promotionsRepository;

  public void evaluatePromotionCreation(
          //receive message
  ) {
    //1. if stock updates and stock is beneath the threshold, increase price
    //2. if sale is made and stock is beneath the threshold, increase price
    //3. if stock updates and stock is above the threshold, lower price
    //4. if sale is made and stock is above the threshold, lower price
    //5. if no evaluation is met, maintain the stock price.

    //5. go to create promotion with criteria
  }

  private void createPromotion(
          PromotionCriteria criteria
  ) {
    //1. search if promotion for the product exists, if so, do nothing.
    //2. if promotion doesn't exist, create the promotion.
    //3. use the given criteria to create the promotion.
  }

}
