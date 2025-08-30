package com.thanhpham.Inventory.controller;

import com.thanhpham.Inventory.dto.event.InventoryCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final StreamBridge streamBridge;

    @PostMapping("/test")
    public void sendCreateInventory(@RequestBody InventoryCreateEvent event) {
        streamBridge.send("processOrderEvent-out-0", event);
    }
}
