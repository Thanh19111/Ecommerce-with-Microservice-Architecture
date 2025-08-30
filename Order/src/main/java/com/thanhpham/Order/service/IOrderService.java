package com.thanhpham.Order.service;

import com.thanhpham.Order.dto.request.OrderCreateRequest;
import com.thanhpham.Order.dto.response.OrderResponse;
import com.thanhpham.Order.entity.Order;

public interface IOrderService {
    public String createOrder(OrderCreateRequest request);
    public String cancelOrder(Long id);
    public OrderResponse findOrderById(Long id);
}
