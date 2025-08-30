package com.thanhpham.Order.controller;

import com.thanhpham.Order.dto.request.OrderCreateRequest;
import com.thanhpham.Order.dto.response.APIResponse;
import com.thanhpham.Order.dto.response.OrderResponse;
import com.thanhpham.Order.service.imp.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<OrderResponse>> findOrderById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<OrderResponse>builder()
                        .message("Success")
                        .result(orderService.findOrderById(id))
                        .build());
    }

    @PostMapping
    public ResponseEntity<APIResponse<String>> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result(orderService.createOrder(orderCreateRequest))
                        .build());
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<APIResponse<String>> cancelOrder(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result(orderService.cancelOrder(id))
                        .build());
    }

}
