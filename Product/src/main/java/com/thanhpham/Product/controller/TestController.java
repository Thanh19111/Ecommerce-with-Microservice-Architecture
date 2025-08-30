package com.thanhpham.Product.controller;

import com.thanhpham.Product.dto.response.APIResponse;
import com.thanhpham.Product.entity.ProductVariant;
import com.thanhpham.Product.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class TestController {
    private final ProductVariantRepository productVariantRepository;

    @DeleteMapping("/test/{id}")
    public ResponseEntity<APIResponse<String>> deleteProduct(@PathVariable Long id) {
        productVariantRepository.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result("OK")
                        .build());
    }
}
