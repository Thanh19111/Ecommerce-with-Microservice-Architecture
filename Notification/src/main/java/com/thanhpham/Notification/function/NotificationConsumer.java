package com.thanhpham.Notification.function;

import com.thanhpham.Notification.model.NotificationRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class NotificationConsumer {
    @Bean
    public Consumer<NotificationRequest> notification() {
        return request -> {
            System.out.println("📥 Nhận notification từ Kafka:");
            System.out.println("✉️ To: " + request.getEmail());
            System.out.println("📨 Message: " + request.getMessage());
            // Có thể xử lý gửi email ở đây nếu cần
        };
    }
}
