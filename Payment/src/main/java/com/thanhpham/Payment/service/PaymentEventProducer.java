package com.thanhpham.Payment.service;

import com.thanhpham.Payment.model.PaymentCompletedEvent;
import com.thanhpham.Payment.model.PaymentFailedEvent;
import com.thanhpham.Payment.util.KafkaMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventProducer {

    private KafkaMessageBuilder messageBuilder;
    private StreamBridge streamBridge;

    public void publishEvent(PaymentCompletedEvent event) {
        Message<PaymentCompletedEvent> message = messageBuilder.buildMessage(event, event.getOrderId());
        streamBridge.send("payment-out-0", message);
    }

    public void publishEvent(PaymentFailedEvent event) {
        Message<PaymentFailedEvent> message = messageBuilder.buildMessage(event, event.getOrderId());
        streamBridge.send("payment-out-0", message);
    }

}
