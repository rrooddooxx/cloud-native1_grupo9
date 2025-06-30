package com.skravetz.cn1grupo9.repository;

import com.skravetz.cn1grupo9.entity.PriceChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceChangeLogRepository extends JpaRepository<PriceChangeLog, Long> {
    
    List<PriceChangeLog> findByProductId(Integer productId);
    
    @Query("SELECT p FROM PriceChangeLog p WHERE p.changeDate BETWEEN :startDate AND :endDate ORDER BY p.changeDate DESC")
    List<PriceChangeLog> findByChangeDateBetween(@Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT p FROM PriceChangeLog p ORDER BY p.changeDate DESC")
    List<PriceChangeLog> findAllOrderByChangeDateDesc();
}