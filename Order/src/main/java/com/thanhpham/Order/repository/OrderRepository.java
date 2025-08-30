package com.thanhpham.Order.repository;

import com.thanhpham.Order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
