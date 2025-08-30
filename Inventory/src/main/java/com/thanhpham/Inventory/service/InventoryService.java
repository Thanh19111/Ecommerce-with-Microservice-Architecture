package com.thanhpham.Inventory.service;

import com.thanhpham.Inventory.dto.request.InventoryCreateRequest;
import com.thanhpham.Inventory.dto.request.InventoryUpdateRequest;
import com.thanhpham.Inventory.dto.response.InventoryResponse;
import com.thanhpham.Inventory.dto.response.InventoryStockResponse;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    InventoryResponse getInventoryByVariantId(Long variantId);
    String releaseInventory(InventoryUpdateRequest request);
    String createInventory(InventoryCreateRequest inventoryCreateRequest);
    String reserveInventory(InventoryUpdateRequest inventoryCreateRequest);
    boolean isLowStock(Long variantId);
    InventoryStockResponse findStockDetailsByVariantId(Long variantId);
    void reserveItems(List<InventoryUpdateRequest> requests);
    void releaseItems(List<InventoryUpdateRequest> requests);
}
