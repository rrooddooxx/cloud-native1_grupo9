package com.grupo9.bff.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Integer productId;
    private String productTitle;
    private Integer stock;
    private Integer reserved;
    private Integer available;
    private String status;
}