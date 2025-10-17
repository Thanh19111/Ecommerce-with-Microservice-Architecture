package com.thanhpham.Order.dto.event;

import lombok.Data;

@Data
public class PaymentFailedEvent {
    private Long orderId;
}
