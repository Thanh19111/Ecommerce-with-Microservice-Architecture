package com.thanhpham.Product.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageCreateEvent {
    private Long variantId;
    private Long imageId;
    private MultipartFile file;
}
