package com.solemate.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.solemate.entity.Order;
import com.solemate.entity.Product;
import com.solemate.repository.OrderRepository;
import com.solemate.repository.ProductRepository;

@Service
public class AdminService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public AdminService(
            ProductRepository productRepository,
            OrderRepository orderRepository) {

        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Map<String, Object> getDashboardStats() {

        long totalProducts =
                productRepository.count();

        long totalOrders =
                orderRepository.count();

        List<Order> orders =
                orderRepository.findAll();

        BigDecimal totalRevenue =
                orders.stream()
                        .map(Order::getTotalAmount)
                        .filter(amount -> amount != null)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        long lowStockProducts =
                productRepository.findAll()
                        .stream()
                        .filter(product ->
                                product.getStock() != null &&
                                product.getStock() <= 5
                        )
                        .count();
                        long pendingOrders =
        orders.stream()
                .filter(order ->
                        order.getStatus() != null &&
                        !order.getStatus().equals("DELIVERED")
                )
                .count();

long deliveredOrders =
        orders.stream()
                .filter(order ->
                        "DELIVERED".equals(order.getStatus())
                )
                .count();

long totalInventory =
        productRepository.findAll()
                .stream()
                .map(Product::getStock)
                .filter(stock -> stock != null)
                .mapToLong(Integer::longValue)
                .sum();
        

        Map<String, Object> stats =
                new HashMap<>();

        stats.put("totalProducts", totalProducts);
        stats.put("totalOrders", totalOrders);
        stats.put("totalRevenue", totalRevenue);
        stats.put("lowStockProducts", lowStockProducts);
        stats.put("pendingOrders", pendingOrders);
        stats.put("deliveredOrders", deliveredOrders);
        stats.put("totalInventory", totalInventory);    

        return stats;
    }
}
