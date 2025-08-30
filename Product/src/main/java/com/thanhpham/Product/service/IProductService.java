package com.thanhpham.Product.service;

import com.thanhpham.Product.dto.request.ProductCreateRequest;
import com.thanhpham.Product.dto.request.ProductUpdateRequest;
import com.thanhpham.Product.dto.response.ProductResponse;
import com.thanhpham.Product.dto.response.ProductTreeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    String createProduct(ProductCreateRequest productCreateRequest);
    ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest);
    ProductResponse findByProductId(Long id);
    ProductTreeResponse findAllProductByCategoryId(Long id);
    String deleteProduct(Long id);
    String updateImageUrl(Long id, String url);
    String createProductImage(Long id, List<MultipartFile> files);
}
