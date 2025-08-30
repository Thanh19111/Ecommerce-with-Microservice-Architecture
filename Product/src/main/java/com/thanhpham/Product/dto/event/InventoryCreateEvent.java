package com.thanhpham.Product.dto.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryCreateEvent {
    private Long id;
    private Integer quantity;
    private Integer reserved;
    private Integer threshold;
}
