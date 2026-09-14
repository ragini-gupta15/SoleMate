package com.solemate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solemate.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}