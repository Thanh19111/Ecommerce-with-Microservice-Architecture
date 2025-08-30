package com.thanhpham.Payment.service;

import com.thanhpham.Payment.model.PaymentCompletedEvent;
import com.thanhpham.Payment.model.PaymentRequestEvent;
import com.thanhpham.Payment.util.KafkaMessageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

    private final KafkaMessageBuilder kafkaMessageBuilder;
    private final StreamBridge streamBridge;

    @Bean
    Consumer<PaymentRequestEvent> processPayment(){
        return event -> {
            try{
                //co the throw exception
                //xu ly payment o day
                log.info(event.toString());
                Message<PaymentCompletedEvent> message = kafkaMessageBuilder.buildMessageWithHeaders(
                        new PaymentCompletedEvent(event.getOrderId(), "COMPLETED"),
                        event.getOrderId(), Map.of(
                                "eventType", "PAYMENT_SUCCESS",
                                "correlationId", UUID.randomUUID().toString()
                        )
                );
                streamBridge.send("payment-out-0",message);
                log.info("Sent payment success event for orderId: {}", event.getOrderId());
            }catch (Exception e){
                log.error(e.getMessage());
                Message<PaymentCompletedEvent> message = kafkaMessageBuilder.buildMessageWithHeaders(
                        new PaymentCompletedEvent(event.getOrderId(), "FAILED"),
                        event.getOrderId(), Map.of(
                                "eventType", "PAYMENT_FAILURE",
                                "correlationId", UUID.randomUUID().toString()
                        )
                );
                streamBridge.send("payment-out-0",message);
                log.info("Sent payment failure event for orderId: {}", event.getOrderId());
            }
        };
    }
}
