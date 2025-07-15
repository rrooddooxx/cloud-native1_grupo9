package com.grupo9.promotionsmscn1grupo9.promotions;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PromotionsRepository extends JpaRepository<PromotionsEntity, String> {
  @Query(
      "SELECT p.promotionId, p.promotionPrice, p.percentageApplied FROM PromotionsEntity p WHERE p.status = "
          + ":status")
  Optional<List<PromotionsEntity>> findAllByStatus(@Param("status") String status);
}
