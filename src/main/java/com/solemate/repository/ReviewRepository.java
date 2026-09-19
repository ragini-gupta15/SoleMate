package com.solemate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solemate.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductIdOrderByCreatedAtDesc(Long productId);
}