package com.thanhpham.Product.dto.request;

import lombok.Data;

@Data
public class CategoryCreateRequest {
    private String name;
    private String description;
    private Long parentId;
}
