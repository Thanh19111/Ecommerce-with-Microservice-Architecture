package com.thanhpham.Product.dto.response;

import com.thanhpham.Product.entity.Category;
import com.thanhpham.Product.entity.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;

    public static CategoryResponse fromEntity(Category category) {
        CategoryResponse dto = new CategoryResponse();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
