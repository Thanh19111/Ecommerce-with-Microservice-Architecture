package com.thanhpham.Product.repository;

import com.thanhpham.Product.entity.Product;
import com.thanhpham.Product.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant,Long> {
}
