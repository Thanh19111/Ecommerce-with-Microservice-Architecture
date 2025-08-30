package com.thanhpham.Product.service;

import com.thanhpham.Product.dto.event.InventoryCreateEvent;
import com.thanhpham.Product.dto.event.ProductImageCreateEvent;
import com.thanhpham.Product.entity.ProductImage;
import com.thanhpham.Product.entity.ProductVariant;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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

    public void sendCreateProductImage(ProductVariant variant, ProductImage productImage, MultipartFile file) {
        ProductImageCreateEvent event = new ProductImageCreateEvent(variant.getId(), productImage.getId(), file);

        streamBridge.send("sendCreateProductImage-out-0", event);
        System.out.println("Send event: " + event);
    }
}
