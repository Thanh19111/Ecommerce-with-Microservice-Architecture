package com.thanhpham.Gateway.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class GetAllEndPoint {
    @SafeVarargs
    public final String[] getAllEndpoints(List<String>... lists) {
        return Arrays.stream(lists)
                .flatMap(List::stream)
                .toArray(String[]::new);
    }
}
