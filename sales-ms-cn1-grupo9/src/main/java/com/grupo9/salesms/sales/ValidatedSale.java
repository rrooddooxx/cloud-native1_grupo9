package com.grupo9.salesms.sales;

import com.grupo9.salesms.product.Product;
import lombok.Builder;

@Builder
public record ValidatedSale(Boolean status, Sale sale, Product product) {}
