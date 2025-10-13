package com.thanhpham.Product.controller;

import com.thanhpham.Product.dto.response.ApiResponse;
import com.thanhpham.Product.dto.response.ProductVariantResponse;
import com.thanhpham.Product.service.IProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("variants")
public class ProductVariantController {

    private final IProductVariantService productVariantService;

    @GetMapping("/price/{variantId}")
    public ResponseEntity<BigDecimal> getPrice(@PathVariable Long variantId){
        return ResponseEntity
                .status(200)
                .body(productVariantService.getPrice(variantId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductVariantResponse>> createProductImages(@RequestParam("id") Long id, @RequestParam("files") List<MultipartFile> files){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.<ProductVariantResponse>builder()
                        .message("Success")
                        .result(productVariantService.createProductImages(id, files))
                        .build());
    }
}
