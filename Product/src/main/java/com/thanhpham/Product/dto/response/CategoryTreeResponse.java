package com.thanhpham.Product.dto.response;

import com.thanhpham.Product.entity.Category;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryTreeResponse {
    private Long id;
    private String name;
    private String description;
    private List<CategoryTreeResponse> children = new ArrayList<>();

    public CategoryTreeResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();

        if (category.getChildren() != null) {
            for (Category child : category.getChildren()) {
                this.children.add(new CategoryTreeResponse(child));
            }
        }
    }
}

