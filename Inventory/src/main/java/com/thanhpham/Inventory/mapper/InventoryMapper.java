package com.thanhpham.Inventory.mapper;

import com.thanhpham.Inventory.dto.event.InventoryCreateEvent;
import com.thanhpham.Inventory.dto.request.InventoryCreateRequest;
import com.thanhpham.Inventory.dto.request.InventoryUpdateRequest;

public class InventoryMapper {
    public static InventoryCreateRequest fromEvent(InventoryCreateEvent event)
    {
        return new InventoryCreateRequest(event.getId(),event.getQuantity(),event.getReserved(),event.getThreshold());
    }


    public static InventoryUpdateRequest fromEvent(InventoryUpdateRequest event) {
        return new InventoryUpdateRequest(event.getId(), event.getNumber());
    }
}
