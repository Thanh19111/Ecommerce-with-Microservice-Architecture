package com.thanhpham.Product.repository;

import com.thanhpham.Product.entity.AttributeValue;
import com.thanhpham.Product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue,Long> {
}
