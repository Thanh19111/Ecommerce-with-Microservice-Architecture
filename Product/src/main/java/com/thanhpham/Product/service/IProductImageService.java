package com.thanhpham.Product.service;

import com.thanhpham.Product.entity.Product;
import com.thanhpham.Product.entity.ProductVariant;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductImageService {
    Product createThumbnail(Product product, MultipartFile file);
    ProductVariant createProductImages(ProductVariant productVariant, List<MultipartFile> files);
}
