package com.thanhpham.Chat.entity;

import lombok.Data;

@Data
public class ProductDetail{
    private String id;
    private String productName;
    private String calculationUnit;
    private int quantity;
    private double price;
}
