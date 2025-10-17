package com.thanhpham.Order.service;

import com.thanhpham.Order.dto.event.PaymentFailedEvent;
import com.thanhpham.Order.dto.event.PaymentSuccessEvent;
import com.thanhpham.Order.entity.Order;
import com.thanhpham.Order.entity.OrderStatus;
import com.thanhpham.Order.repository.OrderRepository;
import com.thanhpham.Order.service.imp.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Bean
    public Consumer<PaymentSuccessEvent> processSuccessPaymentEvent() {
        return event -> {
            Optional<Order> orderOptional = orderRepository.findById(event.getOrderId());
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                order.setStatus(OrderStatus.CONFIRMED);
                orderRepository.save(order);
            } else {
                System.err.println("Order not found with ID: " + event.getOrderId());
            }
        };
    }

    @Bean
    public Consumer<PaymentFailedEvent> processFailedPaymentEvent() {
        return event -> {
            orderService.cancelOrder(event.getOrderId());
        };
    }

}
