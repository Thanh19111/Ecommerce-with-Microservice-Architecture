package com.thanhpham.Payment.service;

import com.thanhpham.Payment.dto.request.OrderCreateEvent;
import com.thanhpham.Payment.dto.request.PaymentFailedEvent;
import com.thanhpham.Payment.dto.request.PaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class PaymentEventConsumer {
    private final StreamBridge streamBridge;

    @Bean
    public Consumer<OrderCreateEvent> processOrderPayment() {
        return event -> {
            try{
                // xu ly thanh toan
                System.out.println(">> Thanh toán thành công");
                System.out.println(event.getOrderId());
                // gia lap thanh toan thanh cong
                PaymentSuccessEvent successEvent = new PaymentSuccessEvent();
                successEvent.setOrderId(event.getOrderId());
                streamBridge.send("sendSuccessPayment-out-0",
                        MessageBuilder
                                .withPayload(successEvent)
                                .setHeader("partitionKey", event.getOrderId()) // giữ đúng orderId
                                .setHeader("eventType", "OrderSuccess")
                                .build());
            }catch (Exception e){
                System.out.println(">> Thanh toán thất bại");
                PaymentFailedEvent failedEvent = new PaymentFailedEvent();
                failedEvent.setOrderId(event.getOrderId());
                streamBridge.send("sendFailedPayment-out-0",
                        MessageBuilder
                                .withPayload(failedEvent)
                                .setHeader("partitionKey", event.getOrderId()) // giữ đúng orderId
                                .setHeader("eventType", "OrderSuccess")
                                .build());
            }
        };
    }

}
