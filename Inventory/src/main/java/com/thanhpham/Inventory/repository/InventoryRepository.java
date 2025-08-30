package com.thanhpham.Inventory.repository;

import com.thanhpham.Inventory.dto.response.InventoryStockResponse;
import com.thanhpham.Inventory.entity.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inventory i WHERE i.id = :id")
    Optional<Inventory> findAndWriteLockByVariantId(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT i FROM Inventory i WHERE i.id = :id")
    Optional<Inventory> findAndReadLockByVariantId(@Param("id") Long id);

    @Query("SELECT i.quantity FROM Inventory i WHERE i.id = :id")
    Integer findStockByVariantId(@Param("id") Long id);

    @Query("SELECT (i.quantity - i.reserved) FROM Inventory i WHERE i.id = :id")
    Integer findAvailableStockByVariantId(@Param("id") Long id);

    @Query("SELECT new com.thanhpham.Inventory.dto.response.InventoryStockResponse(i.quantity, i.reserved) FROM Inventory i WHERE i.id = :id")
    Optional<InventoryStockResponse> findStockDetailsByVariantId(@Param("id") Long id);
}
