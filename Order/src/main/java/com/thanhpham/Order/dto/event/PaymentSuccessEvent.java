package com.thanhpham.Order.dto.event;

import lombok.Data;

@Data
public class PaymentSuccessEvent {
    private Long orderId;
}
