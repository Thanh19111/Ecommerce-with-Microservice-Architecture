package com.thanhpham.Product.service;

import java.math.BigDecimal;

public interface IProductVariantService {
    public BigDecimal getPrice(Long productVariantId);
}
