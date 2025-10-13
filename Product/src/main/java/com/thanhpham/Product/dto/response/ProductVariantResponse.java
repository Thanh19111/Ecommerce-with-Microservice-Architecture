package com.thanhpham.Product.dto.response;

import com.thanhpham.Product.entity.Product;
import com.thanhpham.Product.entity.ProductAttributeValue;
import com.thanhpham.Product.entity.ProductImage;
import com.thanhpham.Product.entity.ProductVariant;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductVariantResponse {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private List<ProductImageResponse> images;
    private List<ProductAttributeValueResponse> attributes;

    public static ProductVariantResponse fromEntity(ProductVariant productVariant) {
        ProductVariantResponse dto = new ProductVariantResponse();
        dto.setId(productVariant.getId());
        dto.setPrice(productVariant.getPrice());
        dto.setSkuCode(productVariant.getSkuCode());
        List<ProductImageResponse> images = new ArrayList<>();
        List<ProductAttributeValueResponse> productAttributeValueResponseList = new ArrayList<>();

        for (ProductImage productImage : productVariant.getImages()) {
            images.add(ProductImageResponse.fromEntity(productImage));
        }
        dto.setImages(images);

        for (ProductAttributeValue productAttributeValue: productVariant.getAttributes()){
            productAttributeValueResponseList.add(ProductAttributeValueResponse.fromEntity(productAttributeValue));
        }
        dto.setAttributes(productAttributeValueResponseList);
        return dto;
    }
}
