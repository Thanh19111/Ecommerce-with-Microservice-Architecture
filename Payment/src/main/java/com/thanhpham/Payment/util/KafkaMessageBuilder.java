package com.thanhpham.Payment.util;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class KafkaMessageBuilder {

    public <T> Message<T> buildMessage(T payload, String key) {
        return MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.KEY, key.getBytes(StandardCharsets.UTF_8))
                .build();
    }

    public <T> Message<T> buildMessageWithHeaders(T payload, String key, Map<String, Object> extraHeaders) {
        MessageBuilder<T> builder = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.KEY, key.getBytes(StandardCharsets.UTF_8));

        if (extraHeaders != null) {
            extraHeaders.forEach(builder::setHeader);
        }

        return builder.build();
    }
}

