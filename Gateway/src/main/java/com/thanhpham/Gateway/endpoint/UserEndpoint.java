package com.thanhpham.Gateway.endpoint;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserEndpoint {

    public static List<String> ADMIN_GET_ENDPOINTS = List.of(
                "/user/users/search",
                "/user/users/{id}"
    );

    public static List<String> USER_GET_ENDPOINTS = List.of(
            "/user/users/my-info"
    );


    public static List<String> ADMIN_PUT_ENDPOINTS = List.of(
                "/user/users/ban",
                "/user/users/deactivate"
    );

    public static List<String> USER_PUT_ENDPOINTS = List.of(
            "/user/user/update/me"
    );

    //-----------------------------------------------------------------------//

    public static List<String> PUBLIC_POST_ENDPOINTS = List.of(
            "/user/users/register"
    );

}
