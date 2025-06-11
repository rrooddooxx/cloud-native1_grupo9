package com.grupo9.bff.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReportDTO {
    private Integer totalProducts;
    private Integer totalSales;
    private Integer totalPurchases;
    private Long totalIncomeAmount;
    private String totalIncomeAmountCurrency;
}