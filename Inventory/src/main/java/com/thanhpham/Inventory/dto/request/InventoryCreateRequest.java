package com.thanhpham.Inventory.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryCreateRequest {
    private Long id;
    private Integer quantity;
    private Integer reserved;
    private Integer threshold;
}
