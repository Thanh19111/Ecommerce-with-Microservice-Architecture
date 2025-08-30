package com.thanhpham.Product.dto.response;

import com.thanhpham.Product.entity.Category;
import com.thanhpham.Product.entity.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//DTO này được sử dụng trong ProductService trong việc tìm các sản phẩm có categoryId được nhập
@Data
public class ProductTreeResponse {
    private Long id;
    private String name;
    private String description;
    private List<ProductResponse> productList;
    private List<ProductTreeResponse> children = new ArrayList<>();

    public ProductTreeResponse(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        if (category.getChildren() != null) {
            for (Category child : category.getChildren()) {
                this.children.add(new ProductTreeResponse(child));
            }
        }
    }
}

