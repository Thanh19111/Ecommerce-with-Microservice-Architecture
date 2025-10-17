package com.thanhpham.Order.dto.response;

import com.thanhpham.Order.entity.Order;
import com.thanhpham.Order.entity.OrderItem;
import com.thanhpham.Order.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private String userId;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> orderItems;

    public static OrderResponse fromEntity(Order order)
    {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setUserId(order.getUserId());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setCreatedAt(order.getCreatedAt());
        if(!order.getOrderItems().isEmpty())
        {
            List<OrderItem> orderItems = order.getOrderItems();
            List<OrderItemResponse> items = new ArrayList<>();
            for(OrderItem orderItem: orderItems)
            {
                items.add(OrderItemResponse.fromEntity(orderItem));
            }
            orderResponse.setOrderItems(items);
        }
        return orderResponse;
    }
}
