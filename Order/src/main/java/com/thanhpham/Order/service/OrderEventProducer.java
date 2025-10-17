package com.thanhpham.Order.service;

import com.thanhpham.Order.dto.event.OrderCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {
    private final StreamBridge streamBridge;

    public void sendPaymentEvent(Long orderId) {
        OrderCreateEvent event = new OrderCreateEvent();
        event.setOrderId(orderId);
        streamBridge.send("sendPaymentEvent-out-0",
                MessageBuilder
                        .withPayload(event)
                        .setHeader("partitionKey", orderId) // giữ đúng orderId
                        .setHeader("eventType", "OrderCreated")
                        .build());
        System.out.println(">> PAYMENT");
        System.out.println(event);
    }
}
