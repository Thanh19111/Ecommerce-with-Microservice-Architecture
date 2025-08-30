package com.thanhpham.Order.dto.response;

import com.thanhpham.Order.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemResponse {
    private Long variantId;
    private Integer quantity;
    private BigDecimal price;

    public static OrderItemResponse fromEntity(OrderItem orderItem)
    {
        return new OrderItemResponse(
                orderItem.getVariantId(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }
}
