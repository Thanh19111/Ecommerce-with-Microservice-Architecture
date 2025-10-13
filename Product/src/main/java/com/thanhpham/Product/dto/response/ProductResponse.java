package com.thanhpham.Product.dto.response;

import com.thanhpham.Product.entity.Category;
import com.thanhpham.Product.entity.Product;
import com.thanhpham.Product.entity.ProductAttributeValue;
import com.thanhpham.Product.entity.ProductVariant;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private String imageUrl;
    private CategoryResponse category;
    private List<ProductVariantResponse> variants;

    public static ProductResponse fromEntity(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setBrand(product.getBrand());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategory(CategoryResponse.fromEntity(product.getCategory()));
        List<ProductVariant> productVariants = product.getVariants();
        List<ProductVariantResponse> responses = new ArrayList<>();

        for(ProductVariant variantResponse : productVariants){
            responses.add(ProductVariantResponse.fromEntity(variantResponse));
        }
        dto.setVariants(responses);
        return dto;
    }
}
