package com.thanhpham.Inventory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryStockResponse {
    private Integer quantity;
    private Integer reserved;
}
