package com.thanhpham.Order.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReservedFailEvent {
    private Long orderId;
    private String reason;
}
