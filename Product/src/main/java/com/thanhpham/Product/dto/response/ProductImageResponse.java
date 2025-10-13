package com.thanhpham.Product.dto.response;

import com.thanhpham.Product.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductImageResponse {
    private Long id;
    private String imageUrl;
    private String description;

    public static ProductImageResponse fromEntity(ProductImage productImage){
        return new ProductImageResponse(productImage.getId(), productImage.getImageUrl(), productImage.getDescription());
    }
}
