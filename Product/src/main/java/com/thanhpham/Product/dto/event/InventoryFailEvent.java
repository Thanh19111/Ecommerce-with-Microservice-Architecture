package com.thanhpham.Product.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryFailEvent {
    private Long variantId;
    private String reason;
}
