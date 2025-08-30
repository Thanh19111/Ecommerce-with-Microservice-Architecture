package com.thanhpham.Order.service;

import com.thanhpham.Order.dto.event.InventoryCanceledFailEvent;
import com.thanhpham.Order.dto.event.InventoryReservedEvent;
import com.thanhpham.Order.dto.event.InventoryReservedFailEvent;
import com.thanhpham.Order.entity.Order;
import com.thanhpham.Order.entity.OrderStatus;
import com.thanhpham.Order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class InventoryEventConsumer {

    private final OrderRepository orderRepository;

    @Bean
    public Consumer<InventoryReservedFailEvent> processReservedInventoryFailEvent() {
        return event -> {
            orderRepository.deleteById(event.getOrderId());
            System.err.println(event.getReason());
        };
    }


    @Bean
    public Consumer<InventoryCanceledFailEvent> processCanceledInventoryFailEvent() {
        return event -> {
            Optional<Order> orderOptional = orderRepository.findById(event.getOrderId());
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                order.setStatus(OrderStatus.FAILED_CANCEL);
                orderRepository.save(order);
            } else {
                System.err.println("Order not found with ID: " + event.getOrderId());
            }
        };
    }



}
