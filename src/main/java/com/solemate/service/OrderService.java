package com.solemate.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solemate.entity.Order;
import com.solemate.entity.OrderItem;
import com.solemate.entity.Product;
import com.solemate.repository.OrderItemRepository;
import com.solemate.repository.OrderRepository;
import com.solemate.repository.ProductRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;


    private static final Set<String> VALID_STATUSES = Set.of(
            "PLACED",
            "CONFIRMED",
            "PACKED",
            "OUT_FOR_DELIVERY",
            "DELIVERED"
    );


    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }


    /* =====================================================
       GET ALL ORDERS
       ===================================================== */

    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }


    /* =====================================================
       GET ORDER BY ID
       ===================================================== */

    public Optional<Order> getOrderById(Long id) {

        return orderRepository.findById(id);
    }


    /* =====================================================
       CREATE ORDER
       ===================================================== */

    @Transactional
    public Order createOrder(Order order) {

        order.setStatus("PLACED");

        order.setCreatedAt(
                LocalDateTime.now()
        );


        List<OrderItem> items =
                order.getItems();


        if (items == null || items.isEmpty()) {

            throw new IllegalArgumentException(
                    "Order must contain at least one item"
            );
        }


        BigDecimal calculatedTotal =
                BigDecimal.ZERO;


        for (OrderItem item : items) {

            /* ---------------------------------------------
               Validate product
               --------------------------------------------- */

            if (item.getProduct() == null ||
                    item.getProduct().getId() == null) {

                throw new IllegalArgumentException(
                        "Product information is required"
                );
            }


            Product product =
                    productRepository.findById(
                            item.getProduct().getId()
                    ).orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Product not found"
                            )
                    );


            /* ---------------------------------------------
               Validate quantity
               --------------------------------------------- */

           if (item.getQuantity() == null ||
        item.getQuantity() <= 0) {

    throw new IllegalArgumentException(
            "Invalid product quantity"
    );
}

if (product.getStock() == null ||
        product.getStock() < item.getQuantity()) {

    throw new IllegalArgumentException(
            "Insufficient stock for "
                    + product.getName()
    );
}
product.setStock(
        product.getStock() - item.getQuantity()
);

productRepository.save(product);


            /* ---------------------------------------------
               Check stock
               --------------------------------------------- */

            if (product.getStock() == null) {

                throw new IllegalArgumentException(
                        "Stock information is unavailable"
                );
            }


            if (item.getQuantity() >
                    product.getStock()) {

                throw new IllegalArgumentException(
                        "Not enough stock for product: "
                                + product.getName()
                );
            }


            /* ---------------------------------------------
               Use actual product from database
               --------------------------------------------- */

            item.setProduct(product);


            /* ---------------------------------------------
               Use actual database price
               --------------------------------------------- */

            BigDecimal productPrice =
                    BigDecimal.valueOf(
                            product.getPrice()
                    );

            item.setPrice(productPrice);


            /* ---------------------------------------------
               Calculate item total
               --------------------------------------------- */

            BigDecimal itemTotal =
                    productPrice.multiply(
                            BigDecimal.valueOf(
                                    item.getQuantity()
                            )
                    );


            calculatedTotal =
                    calculatedTotal.add(
                            itemTotal
                    );


            /* ---------------------------------------------
               Reduce inventory
               --------------------------------------------- */

            product.setStock(
                    product.getStock()
                            - item.getQuantity()
            );


            productRepository.save(product);


            /* ---------------------------------------------
               Connect item to order
               --------------------------------------------- */

            item.setOrder(order);
        }


        /* ---------------------------------------------
           Set calculated order total
           --------------------------------------------- */

        order.setTotalAmount(
                calculatedTotal
        );


        /* ---------------------------------------------
           Save order
           --------------------------------------------- */

        Order savedOrder =
                orderRepository.save(order);


        /* ---------------------------------------------
           Save order items
           --------------------------------------------- */

        orderItemRepository.saveAll(items);


        return savedOrder;
    }


    /* =====================================================
       UPDATE ORDER STATUS
       ===================================================== */
public Optional<Order> updateOrderStatus(
        Long id,
        String status) {

    String normalizedStatus =
            status.trim().toUpperCase();

    if (!VALID_STATUSES.contains(normalizedStatus)) {
        throw new IllegalArgumentException(
                "Invalid order status"
        );
    }

    return orderRepository.findById(id)
            .map(order -> {

                order.setStatus(normalizedStatus);

                return orderRepository.save(order);
            });
}
    /* =====================================================
   GET ORDERS BY CUSTOMER EMAIL
   ===================================================== */

public List<Order> getOrdersByCustomerEmail(
        String email) {

    if (email == null ||
            email.isBlank()) {

        throw new IllegalArgumentException(
                "Email is required"
        );
    }

    return orderRepository
            .findByEmailIgnoreCaseOrderByCreatedAtDesc(
                    email.trim()
            );
}
}