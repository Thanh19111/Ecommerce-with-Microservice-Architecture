package com.thanhpham.Product.repository;

import com.thanhpham.Product.entity.Category;
import com.thanhpham.Product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategory(Category category);
    List<Product> findAllByCategoryId(Long categoryId);
    void deleteByCategory(Category category);
    boolean existsByCategoryId(Long parentId);
}
