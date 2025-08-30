package com.thanhpham.Product.service.imp;

import com.thanhpham.Product.entity.ProductVariant;
import com.thanhpham.Product.exception.ResourceNotFoundException;
import com.thanhpham.Product.repository.ProductVariantRepository;
import com.thanhpham.Product.service.IProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductVariantService implements IProductVariantService {
    private final ProductVariantRepository productVariantRepository;

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getPrice(Long productVariantId) {
        return findById(productVariantId).getPrice();
    }

    @Transactional(readOnly = true)
    private ProductVariant findById(Long productVariantId){
        return productVariantRepository.findById(productVariantId).orElseThrow(
                () -> new ResourceNotFoundException("ProductVariant","ProductVariantID",productVariantId.toString())
        );
    }
}
