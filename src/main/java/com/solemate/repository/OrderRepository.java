package com.solemate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solemate.entity.Order;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    List<Order> findByEmailIgnoreCaseOrderByCreatedAtDesc(
            String email
    );
}