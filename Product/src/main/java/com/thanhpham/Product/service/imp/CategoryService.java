package com.thanhpham.Product.service.imp;

import com.thanhpham.Product.dto.request.CategoryCreateRequest;
import com.thanhpham.Product.dto.request.CategoryUpdateRequest;
import com.thanhpham.Product.dto.response.CategoryResponse;
import com.thanhpham.Product.dto.response.CategoryTreeResponse;
import com.thanhpham.Product.entity.Category;
import com.thanhpham.Product.exception.ResourceNotFoundException;
import com.thanhpham.Product.exception.BadRequestException;
import com.thanhpham.Product.repository.CategoryRepository;
import com.thanhpham.Product.repository.ProductRepository;
import com.thanhpham.Product.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public String createCategory(CategoryCreateRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category","ParentId",request.getParentId().toString()));
            validateParentCategory(request.getParentId());
            category.setParent(parent);
        }
        category =  categoryRepository.save(category);
        return "Category was created successfully";
    }


    @Override
    @Transactional(readOnly = true)
    public CategoryTreeResponse findById(Long id) {
        Category category = findByIdEntity(id);
        return new CategoryTreeResponse(category);
    }

    public Category findByIdEntity(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",id.toString()));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(CategoryUpdateRequest categoryUpdateRequest) {
        Category category = findByIdEntity(categoryUpdateRequest.getId());
        category.setName(categoryUpdateRequest.getName());
        category.setDescription(categoryUpdateRequest.getDescription());
        category =  categoryRepository.save(category);
        return CategoryResponse.fromEntity(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryTreeResponse> getCategoryTree() {
        List<Category> rootCategories = categoryRepository.findByParentIsNull();
        List<CategoryTreeResponse> result = new ArrayList<>();
        for (Category category : rootCategories) {
            result.add(new CategoryTreeResponse(category));
        }
        return result;
    }

    @Transactional
    @Override
    public String deleteCategoryRecursively(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        deleteRecursively(category);
        return "Category was deleted successfully!";
    }

    private void deleteRecursively(Category category) {
        //Đệ quy xóa tất cả danh mục con
        for (Category child : category.getChildren()) {
            deleteRecursively(child);
        }
        //Xóa sản phẩm thuộc danh mục này
        productRepository.deleteByCategory(category);
        //Cuối cùng, xóa danh mục này
        categoryRepository.delete(category);
    }

    public void validateParentCategory(Long parentId){
        // kiểm tra xem có sản phẩm nào trong danh mục vùa nhập không
        boolean hasProducts = productRepository.existsByCategoryId(parentId);
        if (hasProducts) {
            throw new BadRequestException("Cannot add subcategory to category that already contains product");
        }
    }

}
