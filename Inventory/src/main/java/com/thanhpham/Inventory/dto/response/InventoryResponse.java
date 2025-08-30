package com.thanhpham.Inventory.dto.response;

import com.thanhpham.Inventory.entity.Inventory;
import lombok.Data;

@Data
public class InventoryResponse {
    private Long id;
    private Integer quantity;
    private Integer reserved;
    private Integer threshold;

    public static InventoryResponse fromEntity(Inventory inventory)
    {
        InventoryResponse inventoryResponse = new InventoryResponse();
        inventoryResponse.setId(inventory.getId());
        inventoryResponse.setQuantity(inventory.getQuantity());
        inventoryResponse.setReserved(inventory.getReserved());
        inventoryResponse.setThreshold(inventory.getThreshold());
        return inventoryResponse;
    }
}
