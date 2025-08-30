package com.thanhpham.Inventory.service;

import com.thanhpham.Inventory.dto.event.*;
import com.thanhpham.Inventory.dto.request.InventoryCreateRequest;
import com.thanhpham.Inventory.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class InventoryEventConsumer {
    private final InventoryService inventoryService;

    private final StreamBridge streamBridge;

    @Bean
    public Consumer<InventoryCreateEvent> processCreatedProductEvent() {
        return event -> {
            try {
                System.out.println("Inventory created for ID: " + event.getId());
                InventoryCreateRequest request = InventoryMapper.fromEvent(event);
                inventoryService.createInventory(request);
            } catch (Exception ex) {
                System.err.println("Failed to create inventory: " + ex.getMessage());
                InventoryFailEvent failEvent = new InventoryFailEvent(event.getId(), ex.getMessage());
                streamBridge.send("processFailedInventoryEvent-out-0",failEvent);
            }
        };
    }

    @Bean
    public Consumer<InventoryUpdateEvent> processReservedOrderEvent() {
        return event -> {
            try {
                System.out.println("Get message: " + event);
                inventoryService.reserveItems(event.getOrderItemRequestList());
            } catch (Exception ex) {
                System.err.println("Failed to adjust inventory: " + ex.getMessage());
                InventoryFailEvent failEvent = new InventoryFailEvent(event.getOrderId(), ex.getMessage());
                streamBridge.send("processFailedReservedEvent-out-0",failEvent);
            }
        };
    }

    @Bean
    public Consumer<InventoryUpdateEvent> processCanceledOrderEvent() {
        return event -> {
            try {
                System.out.println("Get message: " + event);
                inventoryService.releaseItems(event.getOrderItemRequestList());
            } catch (Exception ex) {
                System.err.println("Failed to adjust inventory: " + ex.getMessage());
                streamBridge.send("processFailedCanceledEvent-out-0",new InventoryCanceledFailEvent(event.getOrderId(), ex.getMessage()));
            }
        };
    }
}
