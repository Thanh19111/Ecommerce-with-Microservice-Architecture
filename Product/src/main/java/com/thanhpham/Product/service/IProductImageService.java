package com.thanhpham.Product.service;

import com.thanhpham.Product.entity.ProductVariant;
import org.springframework.web.multipart.MultipartFile;

public interface IProductImageService {
    String createProductImage(ProductVariant productVariant, MultipartFile file);
    String updateImageUrl(String imageUrl);
}
