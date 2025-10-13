package com.thanhpham.Product.service.imp;

import com.thanhpham.Product.dto.response.ProductVariantResponse;
import com.thanhpham.Product.entity.Product;
import com.thanhpham.Product.entity.ProductImage;
import com.thanhpham.Product.entity.ProductVariant;
import com.thanhpham.Product.exception.BadRequestException;
import com.thanhpham.Product.exception.ResourceNotFoundException;
import com.thanhpham.Product.repository.ProductImageRepository;
import com.thanhpham.Product.repository.ProductRepository;
import com.thanhpham.Product.service.IProductImageService;
import com.thanhpham.Product.service.event.InventoryEventProducer;
import com.thanhpham.Product.service.httpClient.FileClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService {
    private final ProductRepository productRepository;
    private final FileClient fileClient;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createThumbnail(Product product, MultipartFile file) {
        String url = fileClient.createThumbnail(file);
        product.setImageUrl(url);
        return productRepository.save(product);
    }

    @Override
    public ProductVariant createProductImages(ProductVariant productVariant, List<MultipartFile> files){
        long n = productImageRepository.countByProductVariantId(productVariant.getId());
        if(files.size() <= 3 - n){
            List<ProductImage> rs = new ArrayList<>();
            List<String> urls = fileClient.createProductImages(files);
            for (String i : urls){
                ProductImage productImage = new ProductImage();
                productImage.setImageUrl(i);
                productImage.setProductVariant(productVariant);
                rs.add(productImage);
            }
            productImageRepository.saveAll(rs);
            List<ProductImage> temp = productVariant.getImages();
            temp.addAll(rs);
            productVariant.setImages(temp);
            return productVariant;
        }

        throw new BadRequestException("Max product images for product variant");
    }
}
