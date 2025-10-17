package com.thanhpham.Payment.dto.request;

import lombok.Data;

@Data
public class PaymentFailedEvent {
    private Long orderId;
}
