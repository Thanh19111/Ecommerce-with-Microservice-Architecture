package com.thanhpham.Product.dto.response;

import com.thanhpham.Product.entity.Attribute;
import com.thanhpham.Product.entity.AttributeValue;
import com.thanhpham.Product.entity.ProductAttributeValue;
import com.thanhpham.Product.entity.ProductVariant;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ProductAttributeValueResponse {
    private Long id;
    private String attributeName;
    private String value;

    public static ProductAttributeValueResponse fromEntity(ProductAttributeValue productAttributeValue) {
        ProductAttributeValueResponse dto = new ProductAttributeValueResponse();
        dto.setId(productAttributeValue.getId());
        dto.setAttributeName(productAttributeValue.getAttribute().getName());
        dto.setValue(productAttributeValue.getValue().getValue());
        return dto;
    }
}
