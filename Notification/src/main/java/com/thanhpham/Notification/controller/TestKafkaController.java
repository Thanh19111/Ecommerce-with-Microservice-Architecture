package com.thanhpham.Notification.controller;

import com.thanhpham.Notification.model.NotificationRequest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestKafkaController {

    private final StreamBridge streamBridge;

    public TestKafkaController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping
    public String sendNotification(@RequestBody NotificationRequest request) {
        boolean sent = streamBridge.send("notificationProducer-out-0", request);
        return sent ? "✅ Notification sent to Kafka" : "❌ Failed to send notification";
    }
}
