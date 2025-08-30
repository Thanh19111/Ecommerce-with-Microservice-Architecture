package com.thanhpham.Product.dto.request;

import lombok.Data;

@Data
public class CategoryUpdateRequest {
    private Long id;
    private String name;
    private String description;
    private Long parentId;
}
