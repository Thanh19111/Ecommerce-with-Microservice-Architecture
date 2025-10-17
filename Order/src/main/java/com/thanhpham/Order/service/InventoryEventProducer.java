package com.thanhpham.Order.service;

import com.thanhpham.Order.dto.event.InventoryUpdateEvent;
import com.thanhpham.Order.dto.event.OrderCreateEvent;
import com.thanhpham.Order.entity.OrderItem;
import com.thanhpham.Order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryEventProducer {
    private final StreamBridge streamBridge;

    public void sendReservedInventory(Long orderId, List<OrderItem> orderItem) {
        InventoryUpdateEvent event = new InventoryUpdateEvent();
        event.setOrderItemRequestList(orderItem.stream()
                .map(OrderMapper::fromEntity)
                .toList());
        event.setOrderId(orderId);

        streamBridge.send("sendReservedInventory-out-0",
                MessageBuilder
                        .withPayload(event)
                        .setHeader("partitionKey", orderId) // giữ đúng orderId
                        .setHeader("eventType", "OrderCreated")
                        .build());
        System.out.println(event);
    }

    public void sendCancelInventory(Long orderId, List<OrderItem> orderItem) {
        InventoryUpdateEvent event = new InventoryUpdateEvent();
        event.setOrderItemRequestList(orderItem.stream()
                .map(OrderMapper::fromEntity)
                .toList());
        event.setOrderId(orderId);
        streamBridge.send("sendCancelInventory-out-0",
                MessageBuilder
                        .withPayload(event)
                        .setHeader("partitionKey", orderId) // giữ đúng orderId
                        .setHeader("eventType", "OrderCreated")
                        .build());
        System.out.println(event);
    }

}
