package com.thanhpham.Chat.dto.response;

import com.thanhpham.Chat.entity.ProductDetail;
import lombok.Data;

import java.util.List;

@Data
public class BillDetail{
    private String customerName;
    private String address;
    private String phoneNumber;
    private List<ProductDetail> list;
    private long totalAmount;
}
