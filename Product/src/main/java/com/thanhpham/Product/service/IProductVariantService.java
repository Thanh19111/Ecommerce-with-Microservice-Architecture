package com.thanhpham.Product.service;

import com.thanhpham.Product.dto.response.ProductVariantResponse;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface IProductVariantService {
    BigDecimal getPrice(Long productVariantId);
    ProductVariantResponse createProductImages(Long variantId, List<MultipartFile> files);
}
