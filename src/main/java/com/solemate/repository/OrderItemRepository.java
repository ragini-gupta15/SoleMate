package com.solemate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solemate.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}