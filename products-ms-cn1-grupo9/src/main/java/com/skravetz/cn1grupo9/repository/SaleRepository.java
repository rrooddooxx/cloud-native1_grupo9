package com.skravetz.cn1grupo9.repository;

import com.skravetz.cn1grupo9.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    
    Optional<Sale> findByTransactionId(String transactionId);
    
    List<Sale> findByProductId(Integer productId);
    
    List<Sale> findByCustomerId(String customerId);
    
    @Query("SELECT s FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate")
    List<Sale> findSalesBetweenDates(@Param("startDate") LocalDateTime startDate, 
                                   @Param("endDate") LocalDateTime endDate);
}