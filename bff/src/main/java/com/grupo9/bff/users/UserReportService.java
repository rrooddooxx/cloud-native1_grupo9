package com.grupo9.bff.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserReportService {

    public UserReportDTO getUserReport(String userId) {
        log.debug("Getting user report for user: {}", userId);
        
        return UserReportDTO.builder()
                .totalProducts(12)
                .totalSales(8)
                .totalPurchases(5)
                .totalIncomeAmount(2450000L)
                .totalIncomeAmountCurrency("CLP")
                .build();
    }
}