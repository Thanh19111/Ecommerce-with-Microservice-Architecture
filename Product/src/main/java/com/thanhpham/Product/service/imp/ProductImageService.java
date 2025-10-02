package com.thanhpham.Product.service.imp;

import com.thanhpham.Product.entity.ProductImage;
import com.thanhpham.Product.entity.ProductVariant;
import com.thanhpham.Product.repository.ProductImageRepository;
import com.thanhpham.Product.service.IProductImageService;
import com.thanhpham.Product.service.event.InventoryEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService {
    private final ProductImageRepository productImageRepository;
    private final InventoryEventProducer inventoryEventProducer;

    @Override
    public String createProductImage(ProductVariant productVariant, MultipartFile file) {
        ProductImage productImage = new ProductImage();
        productImage.setProductVariant(productVariant);
        productImage = productImageRepository.save(productImage);
        inventoryEventProducer.sendCreateProductImage(productVariant,productImage, file);
        return "Processing";
    }

    @Override
    public String updateImageUrl(String imageUrl) {
        return "";
    }


    public String updateImageUrl(Long variantId, String imageUrl) {
        return "Image updated successfully";
    }
}
