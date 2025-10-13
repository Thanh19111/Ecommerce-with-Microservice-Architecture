package com.thanhpham.Product.repository;

import com.thanhpham.Product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    long countByProductVariantId(Long productVariantId);
}
