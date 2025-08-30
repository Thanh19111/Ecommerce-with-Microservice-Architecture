package com.thanhpham.Order.dto.event;

import com.thanhpham.Order.dto.request.InventoryUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryUpdateEvent {
    private Long orderId;
    private List<InventoryUpdateRequest> orderItemRequestList;
}