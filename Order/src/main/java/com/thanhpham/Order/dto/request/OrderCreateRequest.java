package com.thanhpham.Order.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequest {
    private Long userId;
    private List<OrderItemRequest> items;
}
