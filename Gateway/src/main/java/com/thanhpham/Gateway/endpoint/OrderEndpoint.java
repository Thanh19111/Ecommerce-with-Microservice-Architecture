package com.thanhpham.Gateway.endpoint;

import java.util.List;

public class OrderEndpoint {

    public static List<String> ADMIN_GET_ENDPOINTS = List.of(
            "/order/orders/3"
    );

    public static List<String> ADMIN_PUT_ENDPOINTS = List.of(
                "/order/orders/{id}/cancel"
    );

    //-----------------------------------------------------------------------//

    public static List<String> PUBLIC_POST_ENDPOINTS = List.of(
            "/order/orders"
    );

}
