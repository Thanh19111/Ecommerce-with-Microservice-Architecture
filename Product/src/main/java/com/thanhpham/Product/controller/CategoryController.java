package com.thanhpham.Product.controller;

import com.thanhpham.Product.dto.request.CategoryCreateRequest;
import com.thanhpham.Product.dto.request.CategoryUpdateRequest;
import com.thanhpham.Product.dto.request.ProductCreateRequest;
import com.thanhpham.Product.dto.response.APIResponse;
import com.thanhpham.Product.dto.response.CategoryResponse;
import com.thanhpham.Product.dto.response.CategoryTreeResponse;
import com.thanhpham.Product.service.ICategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/categories", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Tag(name = "CRUD REST APIs for Category Service", description = "CREATE, UPDATE, FETCH and DELETE Product details")
public class CategoryController {

    private final ICategoryService iCategoryService;

    @GetMapping("/tree")
    public ResponseEntity<APIResponse<List<CategoryTreeResponse>>> getCategoryTree() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<List<CategoryTreeResponse>>builder()
                        .message("Success")
                        .result(iCategoryService.getCategoryTree())
                        .build());
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse<String>> createCategory(@RequestBody CategoryCreateRequest categoryCreateRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result(iCategoryService.createCategory(categoryCreateRequest))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteCategoryRecursively(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result(iCategoryService.deleteCategoryRecursively(id))
                        .build());
    }

    @PutMapping("")
    public ResponseEntity<APIResponse<CategoryResponse>> updateCategory(@RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<CategoryResponse>builder()
                        .message("Success")
                        .result(iCategoryService.updateCategory(categoryUpdateRequest))
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryTreeResponse>> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<CategoryTreeResponse>builder()
                        .message("Success")
                        .result(iCategoryService.findById(id))
                        .build());
    }

}
