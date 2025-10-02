package com.thanhpham.Product.service.imp;

import com.thanhpham.Product.dto.request.CreateVariantRequest;
import com.thanhpham.Product.dto.request.ProductCreateRequest;
import com.thanhpham.Product.dto.request.ProductUpdateRequest;
import com.thanhpham.Product.dto.request.VariantAttributeRequest;
import com.thanhpham.Product.dto.response.ProductResponse;
import com.thanhpham.Product.dto.response.ProductTreeResponse;
import com.thanhpham.Product.entity.*;
import com.thanhpham.Product.exception.ResourceNotFoundException;
import com.thanhpham.Product.repository.*;
import com.thanhpham.Product.service.IProductService;
import com.thanhpham.Product.service.event.InventoryEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final InventoryEventProducer inventoryEventProducer;

    @Transactional
    @Override
    public String createProduct(ProductCreateRequest request) {
        //Tạo Product
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(categoryRepository.findById(request.getCategoryId()).orElseThrow(
                ()-> new ResourceNotFoundException("Category","CategoryId",request.getCategoryId().toString())));
        if(categoryRepository.hasChildren(request.getCategoryId()))
        {
            throw new IllegalArgumentException("Only allow adding products with leaf category");
        }

        product.setBrand(request.getBrand());
        product = productRepository.save(product);

        //Tạo các ProductVariant
        for (CreateVariantRequest variantRequest : request.getVariants()) {
            ProductVariant variant = new ProductVariant();
            variant.setPrice(variantRequest.getPrice());
            variant.setImageUrl(UUID.randomUUID().toString());
            variant.setSkuCode(UUID.randomUUID().toString());
            variant.setProduct(product);
            variant = productVariantRepository.save(variant);

            //Mapping variant ↔ attributes
            for (VariantAttributeRequest attr : variantRequest.getAttributes()) {
                ProductAttributeValue pav = new ProductAttributeValue();
                pav.setVariant(variant);
                pav.setAttribute(attributeRepository.findById(attr.getAttributeId()).orElseThrow(
                        () -> new ResourceNotFoundException("Attribute","AttributeId",attr.getAttributeId().toString())
                ));
                pav.setValue(attributeValueRepository.findById(attr.getAttributeValueId()).orElseThrow(
                        () -> new ResourceNotFoundException("AttributeValue","AttributeValueId",attr.getAttributeValueId().toString())
                ));

                productAttributeValueRepository.save(pav);

            }

            inventoryEventProducer.sendCreateInventory(variant, variantRequest.getStock());
        }


        //Trả về kết quả
        return "Product created successfully!";
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest) {
        Product product = findByProductIdEntity(productUpdateRequest.getId());
        product.setName(productUpdateRequest.getName());
        product.setBrand(productUpdateRequest.getBrand());
        product.setDescription(productUpdateRequest.getDescription());
        product = productRepository.save(product);
        return ProductResponse.fromEntity(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findByProductId(Long id) {
        Product product = findByProductIdEntity(id);
        return ProductResponse.fromEntity(product);
    }

    private Product findByProductIdEntity(Long id)
    {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product","ProductId",id.toString())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ProductTreeResponse findAllProductByCategoryId(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category","CategoryId", id.toString())
        );
        ProductTreeResponse productTreeResponse = new ProductTreeResponse(category);
        getDetail(productTreeResponse);
        return productTreeResponse;
    }

    @Override
    @Transactional
    public String deleteProduct(Long id) {

        Product product = findByProductIdEntity(id);
        List<ProductVariant> productVariants = product.getVariants();
        for (ProductVariant productVariant: productVariants)
        {
            List<ProductAttributeValue> attributes = productVariant.getAttributes();
            for (ProductAttributeValue productAttributeValue: attributes)
            {
                productAttributeValueRepository.delete(productAttributeValue);
            }
        }
        productRepository.delete(product);
        return "Product was deleted successfully";
    }

    @Override
    public String updateImageUrl(Long id, String url) {
        return "";
    }

    @Override
    public String createProductImage(Long id, List<MultipartFile> files) {
        return "";
    }


    private void getDetail(ProductTreeResponse category) {
        if (isLeaf(category)) {
            List<Product> products = productRepository.findAllByCategoryId(category.getId());
            List<ProductResponse> productResponses = products.stream()
                    .map(ProductResponse::fromEntity)
                    .toList();
            category.setProductList(productResponses);
        } else {
            for (ProductTreeResponse child : category.getChildren()) {
                getDetail(child);
            }
        }
    }

    private boolean isLeaf(ProductTreeResponse category) {
        return category.getChildren() == null || category.getChildren().isEmpty();
    }

}
