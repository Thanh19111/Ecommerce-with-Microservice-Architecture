package com.thanhpham.Product.service.imp;

import com.thanhpham.Product.dto.response.ProductVariantResponse;
import com.thanhpham.Product.entity.ProductVariant;
import com.thanhpham.Product.exception.ResourceNotFoundException;
import com.thanhpham.Product.repository.ProductVariantRepository;
import com.thanhpham.Product.service.IProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantService implements IProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final ProductImageService productImageService;

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getPrice(Long productVariantId) {
        return findById(productVariantId).getPrice();
    }

    @Transactional(readOnly = true)
    private ProductVariant findById(Long variantId){
        return productVariantRepository.findById(variantId).orElseThrow(
                () -> new ResourceNotFoundException("ProductVariant","ProductVariantID",variantId.toString())
        );
    }

    @Override
    public ProductVariantResponse createProductImages(Long variantId, List<MultipartFile> files){
        ProductVariant productVariant = findById(variantId);
        return ProductVariantResponse.fromEntity(productImageService.createProductImages(productVariant, files));
    }
}
