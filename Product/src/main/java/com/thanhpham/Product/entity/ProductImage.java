package com.thanhpham.Product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_images")
@Getter
@Setter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;
}
