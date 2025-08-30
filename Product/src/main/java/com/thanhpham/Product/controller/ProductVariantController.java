package com.thanhpham.Product.controller;

import com.thanhpham.Product.service.IProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

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
}
