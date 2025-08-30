package com.thanhpham.Inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Inventory {
    @Id
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    private Integer reserved; // Dự phòng (cho đơn hàng đang xử lý)

    private Integer threshold; // Mức cảnh báo tồn kho
}
