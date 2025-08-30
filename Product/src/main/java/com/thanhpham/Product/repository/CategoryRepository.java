package com.thanhpham.Product.repository;

import com.thanhpham.Product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByParentIsNull();

    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.parent.id = :categoryId")
    boolean hasChildren(@Param("categoryId") Long categoryId);
}
