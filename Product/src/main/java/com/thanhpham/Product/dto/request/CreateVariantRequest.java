package com.thanhpham.Product.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateVariantRequest {
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private List<VariantAttributeRequest> attributes;
}
