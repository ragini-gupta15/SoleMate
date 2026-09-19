package com.solemate.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solemate.entity.Order;
import com.solemate.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }


    /* =====================================================
       GET ALL ORDERS
       ===================================================== */

    @GetMapping
    public List<Order> getAllOrders() {

        return orderService.getAllOrders();
    }


    /* =====================================================
       GET ORDER BY ID
       ===================================================== */

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long id) {

        return orderService
                .getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity
                                .notFound()
                                .build()
                );
    }
    /* =====================================================
   GET ORDERS BY CUSTOMER EMAIL
   ===================================================== */

@GetMapping("/customer")
public ResponseEntity<?> getOrdersByCustomerEmail(
        @RequestParam("email") String email) {

    try {

        return ResponseEntity.ok(
                orderService
                        .getOrdersByCustomerEmail(email)
        );

    } catch (IllegalArgumentException e) {

        return ResponseEntity
                .badRequest()
                .body(
                        Map.of(
                                "error",
                                e.getMessage()
                        )
                );
    }
}


    /* =====================================================
       CREATE ORDER
       ===================================================== */

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestBody Order order) {

        try {

            Order savedOrder =
                    orderService.createOrder(order);

            return ResponseEntity.ok(
                    savedOrder
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    e.getMessage()
                            )
                    );
        }
    }


    /* =====================================================
       UPDATE ORDER STATUS
       ===================================================== */

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {


        String status =
                request.get("status");


        if (status == null ||
                status.isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    "Status is required"
                            )
                    );
        }


        try {

            return orderService
                    .updateOrderStatus(
                            id,
                            status
                    )
                    .map(ResponseEntity::ok)
                    .orElse(
                            ResponseEntity
                                    .notFound()
                                    .build()
                    );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    e.getMessage()
                            )
                    );
        }
    }
}