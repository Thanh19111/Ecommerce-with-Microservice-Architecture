package com.thanhpham.Inventory.controller;

import com.thanhpham.Inventory.dto.request.InventoryCreateRequest;
import com.thanhpham.Inventory.dto.request.InventoryUpdateRequest;
import com.thanhpham.Inventory.dto.response.APIResponse;
import com.thanhpham.Inventory.dto.response.InventoryResponse;
import com.thanhpham.Inventory.dto.response.InventoryStockResponse;
import com.thanhpham.Inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{variantId}")
    public ResponseEntity<APIResponse<InventoryResponse>> getInventory(@PathVariable Long variantId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<InventoryResponse>builder()
                        .message("Success")
                        .result(inventoryService.getInventoryByVariantId(variantId))
                        .build());
    }

    @PostMapping("/reserve")
    public ResponseEntity<APIResponse<String>> reserveInventory(@RequestBody InventoryUpdateRequest updateRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result(inventoryService.reserveInventory(updateRequest))
                        .build());
    }

    @PostMapping("/release")
    public ResponseEntity<APIResponse<String>> releaseInventory(@RequestBody InventoryUpdateRequest updateRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result(inventoryService.releaseInventory(updateRequest))
                        .build());
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse<String>> releaseInventory(@RequestBody InventoryCreateRequest createRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result(inventoryService.createInventory(createRequest))
                        .build());
    }

    @PostMapping("/{variantId}/stock")
    public ResponseEntity<APIResponse<InventoryStockResponse>> releaseInventory(@PathVariable Long variantId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<InventoryStockResponse>builder()
                        .message("Success")
                        .result(inventoryService.findStockDetailsByVariantId(variantId))
                        .build());
    }

    @PostMapping("/{variantId}/check")
    public ResponseEntity<APIResponse<Boolean>> isLowStock(@PathVariable Long variantId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<Boolean>builder()
                        .message("Success")
                        .result(inventoryService.isLowStock(variantId))
                        .build());
    }

}
