package com.thanhpham.Order.service.httpClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

//name de anh xa voi cau hinh trong file yml
//url duong dan tinh hoac thay bang url eureka
@FeignClient(name = "product-service", url = "http://localhost:8000")
public interface ProductClient {
    @GetMapping("/variants/price/{variantId}")
    BigDecimal getPrice(@PathVariable("variantId") Long variantId);
}
