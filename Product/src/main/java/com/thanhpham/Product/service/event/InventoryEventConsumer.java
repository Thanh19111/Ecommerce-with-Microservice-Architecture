package com.thanhpham.Product.service.event;

import com.thanhpham.Product.dto.event.InventoryFailEvent;
import com.thanhpham.Product.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class InventoryEventConsumer {

    private final ProductVariantRepository productVariantRepository;

    @Bean
    public Consumer<InventoryFailEvent> processFailInventory() {
        return event -> {
            productVariantRepository.deleteById(event.getVariantId());
            System.out.print(event.getReason());
        };
    }
}
