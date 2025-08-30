package com.thanhpham.Payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestEvent {
    private String orderId;
    private String userId;
    private BigDecimal amount;
}
