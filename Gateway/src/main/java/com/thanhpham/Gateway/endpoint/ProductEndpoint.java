package com.thanhpham.Gateway.endpoint;

import java.util.List;

public class ProductEndpoint {

    public static List<String> ADMIN_POST_ENDPOINTS = List.of(
            "/product/products/create",
            "/product/categories/create"
    );

    public static List<String> ADMIN_PUT_ENDPOINTS = List.of(
            "/product/products",
            "/product/categories"
    );

    public static List<String> ADMIN_DELETE_ENDPOINTS = List.of(
            "/product/products/{id}",
            "/categories/{id}"
    );

    //-----------------------------------------------------------------------//

    public static List<String> PUBLIC_GET_ENDPOINTS = List.of(
            "/product/products/category/{id}",
            "/product/products/{id}",
            "/product/categories/tree",
            "/product/categories/{id}",
            "/product/variants/price/{id}"
    );

}
