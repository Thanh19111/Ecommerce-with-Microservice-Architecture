package com.thanhpham.Gateway.endpoint;

import java.util.List;

public class InventoryEndpoint {

    public static List<String> ADMIN_GET_ENDPOINTS = List.of(
            "/order/orders/{id}"
    );

    public static List<String> ADMIN_POST_ENDPOINTS = List.of(
            "/inventory/inventories/create",
            "inventory/inventories/{id}/check"
    );

    public static List<String> ADMIN_PUT_ENDPOINTS = List.of(
                "/order/orders/{id}/cancel"
    );

    //-----------------------------------------------------------------------//

    public static List<String> PUBLIC_POST_ENDPOINTS = List.of(
            "/inventory/inventories/reserve",
            "/inventory/inventories/release"
    );

    public static List<String> PUBLIC_GET_ENDPOINTS = List.of(
            "/inventory/inventories/{id}"
    );

}
