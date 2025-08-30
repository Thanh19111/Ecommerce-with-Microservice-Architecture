package com.thanhpham.Product.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductCreateRequest {
    private String name;
    private String description;
    private String brand;
    private Long categoryId;

    private List<CreateVariantRequest> variants;
}
