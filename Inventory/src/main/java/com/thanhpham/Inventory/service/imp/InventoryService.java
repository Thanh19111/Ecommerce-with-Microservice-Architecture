package com.thanhpham.Inventory.service.imp;
import com.thanhpham.Inventory.dto.request.InventoryCreateRequest;
import com.thanhpham.Inventory.dto.request.InventoryUpdateRequest;
import com.thanhpham.Inventory.dto.response.InventoryResponse;
import com.thanhpham.Inventory.dto.response.InventoryStockResponse;
import com.thanhpham.Inventory.entity.Inventory;
import com.thanhpham.Inventory.exception.BadRequestException;
import com.thanhpham.Inventory.exception.ResourceNotFoundException;
import com.thanhpham.Inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService implements com.thanhpham.Inventory.service.InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventoryByVariantId(Long id) {
        return InventoryResponse.fromEntity(
                getInventoryByVariantIdEntity(id)
        );
    }

    @Override
    @Transactional
    public String reserveInventory(InventoryUpdateRequest request) {
        Inventory inventory = findInventoryAndWriteLock(request.getId());
        int available = inventory.getQuantity() - inventory.getReserved();
        if (available < request.getNumber()) {
            throw new BadRequestException("Not enough stock");
        }
        inventory.setReserved(inventory.getReserved() + request.getNumber());
        inventoryRepository.save(inventory);
        return "Inventory adjusted successfully";
    }

    @Override
    @Transactional
    public String releaseInventory(InventoryUpdateRequest request) {
        Inventory inventory = findInventoryAndWriteLock(request.getId());
        inventory.setReserved(Math.max(inventory.getReserved() - request.getNumber(), 0));
        inventoryRepository.save(inventory);
        return "Inventory adjusted successfully";
    }


    @Override
    @Transactional
    public String createInventory(InventoryCreateRequest inventoryCreateRequest) {
        boolean exist = inventoryRepository.existsById(inventoryCreateRequest.getId());
        if(exist)
        {
            throw new BadRequestException("Variant with id = " + inventoryCreateRequest.getId() + " already existed");
        }
        Inventory inventory = new Inventory();
        inventory.setId(inventoryCreateRequest.getId());
        inventory.setQuantity(inventoryCreateRequest.getQuantity());
        inventory.setThreshold(inventoryCreateRequest.getThreshold());
        inventory.setReserved(0);
        inventoryRepository.save(inventory);
        return "Inventory created successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLowStock(Long variantId) {
        Inventory inventory = findInventoryAndReadLock(variantId);
        int available = inventory.getQuantity() - inventory.getReserved();
        return available <= inventory.getThreshold();
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryStockResponse findStockDetailsByVariantId(Long variantId)
    {
        return inventoryRepository.findStockDetailsByVariantId(variantId).orElseThrow(
            ()->new ResourceNotFoundException("Variant","VariantId",variantId.toString())
        );
    }

    @Transactional(readOnly = true)
    private Inventory getInventoryByVariantIdEntity(Long variantId){
        return inventoryRepository.findById(variantId).orElseThrow(
                () -> new ResourceNotFoundException("Variant","VariantId",variantId.toString())
        );
    }

    @Transactional
    private Inventory findInventoryAndWriteLock(Long variantId)
    {
        return inventoryRepository.findAndWriteLockByVariantId(variantId).orElseThrow(
                ()->new ResourceNotFoundException("Variant","VariantId",variantId.toString())
        );
    }

    @Transactional(readOnly = true)
    private Inventory findInventoryAndReadLock(Long variantId)
    {
        return inventoryRepository.findAndReadLockByVariantId(variantId).orElseThrow(
                ()->new ResourceNotFoundException("Variant","VariantId",variantId.toString())
        );
    }

    //dùng trong spring cloud function + kafka
    @Transactional
    public void reserveItems(List<InventoryUpdateRequest> requests)
    {
        for(InventoryUpdateRequest request: requests){
            reserveInventory(request);
        }
    }

    @Transactional
    public void releaseItems(List<InventoryUpdateRequest> requests){
        for (InventoryUpdateRequest request: requests){
            releaseInventory(request);
        }
    }
}
