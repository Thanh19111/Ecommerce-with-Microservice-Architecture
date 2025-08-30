package com.thanhpham.Product.service;

import com.thanhpham.Product.dto.request.CategoryCreateRequest;
import com.thanhpham.Product.dto.request.CategoryUpdateRequest;
import com.thanhpham.Product.dto.response.CategoryResponse;
import com.thanhpham.Product.dto.response.CategoryTreeResponse;
import com.thanhpham.Product.dto.response.ProductTreeResponse;
import com.thanhpham.Product.entity.Category;

import java.util.List;

public interface ICategoryService {
    public String createCategory(CategoryCreateRequest request);
    public CategoryTreeResponse findById(Long id);
    public CategoryResponse updateCategory(CategoryUpdateRequest categoryUpdateRequest);
    public List<CategoryTreeResponse> getCategoryTree();
    public String deleteCategoryRecursively(Long categoryId);
}
