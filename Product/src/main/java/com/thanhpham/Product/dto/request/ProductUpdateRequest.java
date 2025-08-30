package com.thanhpham.Product.dto.request;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String description;
    private String brand;
}
