package com.thanhpham.Order.service.httpClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//name de anh xa voi cau hinh trong file yml
//url duong dan tinh hoac thay bang url eureka
@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/exist")
    boolean doesUserExist(@RequestParam("userId") Long userId);
}
