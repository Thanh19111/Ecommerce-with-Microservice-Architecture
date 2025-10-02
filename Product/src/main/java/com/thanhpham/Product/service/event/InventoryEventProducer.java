package com.thanhpham.Product.service.event;

import com.thanhpham.Product.dto.event.InventoryCreateEvent;
import com.thanhpham.Product.entity.ProductVariant;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventProducer {

    private final StreamBridge streamBridge;

    public void sendCreateInventory(ProductVariant variant, Integer stock) {
        InventoryCreateEvent event = new InventoryCreateEvent();
        event.setId(variant.getId());
        event.setQuantity(stock);
        streamBridge.send("sendCreateInventory-out-0", event);
        System.out.println("Send event: " + event);
    }
}
