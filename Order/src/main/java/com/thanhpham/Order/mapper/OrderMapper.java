package com.thanhpham.Order.mapper;

import com.thanhpham.Order.dto.request.InventoryUpdateRequest;
import com.thanhpham.Order.entity.OrderItem;

public class OrderMapper {
    public static InventoryUpdateRequest fromEntity(OrderItem orderItem)
    {
        return new InventoryUpdateRequest(orderItem.getVariantId(), orderItem.getQuantity());
    }
}
