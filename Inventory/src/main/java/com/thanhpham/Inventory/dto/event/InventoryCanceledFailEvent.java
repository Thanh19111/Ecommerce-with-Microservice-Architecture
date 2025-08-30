package com.thanhpham.Inventory.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryCanceledFailEvent {
    private Long orderId;
    private String reason;
}
