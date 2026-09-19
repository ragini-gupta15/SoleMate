package com.solemate.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.solemate.entity.Product;
import com.solemate.repository.ProductRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadProducts(ProductRepository productRepository) {

        return args -> {

            List<Product> products = List.of(

                createProduct(
                    "Air Max 270",
                    "Nike",
                    12999.0,
                    "Running",
                    "Black / White",
                    "7,8,9,10,11",
                    24,
                    "/images/products/air-max-270/1.jpg",
                    "The Air Max 270 combines a bold lifestyle silhouette with responsive cushioning.",
                    true
                ),

                createProduct(
                    "Ultraboost",
                    "Adidas",
                    14999.0,
                    "Running",
                    "Black / White",
                    "7,8,9,10,11",
                    18,
                    "https://images.unsplash.com/photo-1786379582231-f4a593cacf2d?auto=format&fit=crop&w=1200&q=90",
                    "Built for runners who want a smooth and comfortable ride. The Ultraboost-inspired design combines a lightweight upper with a cushioned midsole for everyday training and long-distance comfort.",
                    true
                ),

                createProduct(
                    "Air Force 1 '07",
                    "Nike",
                    10995.0,
                    "Sneakers",
                    "White / Red",
                    "6,7,8,9,10,11",
                    31,
                    "https://images.unsplash.com/photo-1584564515943-b54cbb61836b?auto=format&fit=crop&w=1200&q=90",
                    "A timeless low-top sneaker with a clean silhouette and everyday versatility. The classic construction makes it easy to pair with casual outfits while providing dependable comfort throughout the day.",
                    true
                ),

                createProduct(
                    "Forum Low",
                    "Adidas",
                    8999.0,
                    "Sneakers",
                    "White / Blue",
                    "7,8,9,10,11",
                    16,
                    "https://images.unsplash.com/photo-1726312045271-63d746467b48?auto=format&fit=crop&w=1200&q=90",
                    "Inspired by classic court style, the Forum Low brings a retro basketball aesthetic into a modern everyday sneaker. Its structured design and low-profile silhouette make it a versatile lifestyle choice.",
                    true
                ),

                createProduct(
                    "Dunk Low Retro",
                    "Nike",
                    11995.0,
                    "Sneakers",
                    "Grey / White",
                    "7,8,9,10,11",
                    21,
                    "https://images.unsplash.com/photo-1687444365871-a2e06ff2295a?auto=format&fit=crop&w=1200&q=90",
                    "A clean low-top sneaker inspired by classic basketball footwear. The layered upper, padded collar and low-profile construction make it suitable for everyday streetwear.",
                    false
                ),

                createProduct(
                    "Air Jordan 1 Low",
                    "Jordan",
                    10495.0,
                    "Basketball",
                    "Black / Red / White",
                    "7,8,9,10,11",
                    14,
                    "https://images.unsplash.com/photo-1552346154-21d32810aba3?auto=format&fit=crop&w=1200&q=90",
                    "A low-cut Jordan silhouette combining classic basketball styling with a streamlined everyday profile. The padded construction and flexible outsole provide comfort for casual wear.",
                    true
                ),

                createProduct(
                    "LeBron Witness",
                    "Nike",
                    11999.0,
                    "Basketball",
                    "White / Black",
                    "8,9,10,11",
                    12,
                    "https://images.unsplash.com/photo-1603631540004-d7b2616b2323?auto=format&fit=crop&w=1200&q=90",
                    "Designed for explosive basketball movement, this performance-inspired shoe focuses on stability, cushioning and responsive support during quick changes of direction.",
                    false
                ),

                createProduct(
                    "Metcon 9",
                    "Nike",
                    11995.0,
                    "Training",
                    "Black / White",
                    "7,8,9,10,11",
                    20,
                    "https://images.unsplash.com/photo-1786379582186-83ef57a1c420?auto=format&fit=crop&w=1200&q=90",
                    "A training-focused silhouette designed for gym sessions, strength workouts and high-intensity movement. The stable base and durable construction make it suitable for demanding training routines.",
                    true
                ),

                createProduct(
                    "Nano X4",
                    "Reebok",
                    9999.0,
                    "Training",
                    "Grey / Black",
                    "7,8,9,10,11",
                    17,
                    "https://images.unsplash.com/photo-1642088338903-735e55b45c1a?auto=format&fit=crop&w=1200&q=90",
                    "A versatile training shoe designed for workouts that require stability and flexibility. Its athletic construction makes it suitable for strength training, conditioning and everyday gym sessions.",
                    false
                ),

                createProduct(
                    "574 Core",
                    "New Balance",
                    8499.0,
                    "Lifestyle",
                    "Grey / White",
                    "7,8,9,10,11",
                    27,
                    "https://images.unsplash.com/photo-1685273348445-70737e376cac?auto=format&fit=crop&w=1200&q=90",
                    "A classic lifestyle sneaker with a comfortable everyday profile. Its understated styling works well with casual outfits while the cushioned construction keeps everyday walking comfortable.",
                    false
                ),

                createProduct(
                    "Old Skool",
                    "Vans",
                    6999.0,
                    "Lifestyle",
                    "Black / White",
                    "6,7,8,9,10,11",
                    34,
                    "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?auto=format&fit=crop&w=1200&q=90",
                    "A classic skate-inspired low-top silhouette with a clean, recognizable profile. Designed for everyday casual wear, it combines a durable upper with a comfortable rubber outsole.",
                    false
                ),

                createProduct(
                    "Chuck 70",
                    "Converse",
                    7499.0,
                    "Lifestyle",
                    "Black / White",
                    "6,7,8,9,10,11",
                    22,
                    "https://upload.wikimedia.org/wikipedia/commons/a/a5/Black_Converse_sneakers.JPG",
                    "A timeless canvas sneaker inspired by classic court footwear. Its simple silhouette makes it an easy everyday choice for casual outfits and streetwear looks.",
                    false
                ),

                createProduct(
                    "Gel-Kayano 30",
                    "ASICS",
                    13999.0,
                    "Running",
                    "White / Blue",
                    "7,8,9,10,11",
                    11,
                    "https://images.unsplash.com/photo-1779122873880-b2aa20d95e6c?auto=format&fit=crop&w=1200&q=90",
                    "A stability-focused running silhouette designed for comfortable daily miles. The cushioned construction provides a supportive feel for runners who want a smooth and stable ride.",
                    true
                ),

                createProduct(
                    "Pegasus 41",
                    "Nike",
                    11999.0,
                    "Running",
                    "White / Grey",
                    "7,8,9,10,11",
                    19,
                    "https://images.unsplash.com/photo-1603631540004-d7b2616b2323?auto=format&fit=crop&w=1200&q=90",
                    "A versatile running shoe designed for everyday training. Its lightweight profile and cushioned ride make it suitable for short runs, longer sessions and daily active use.",
                    true
                ),

                createProduct(
                    "574 Sport",
                    "New Balance",
                    9499.0,
                    "Sports",
                    "Black / Grey",
                    "7,8,9,10,11",
                    15,
                    "https://images.unsplash.com/photo-1552346154-21d32810aba3?auto=format&fit=crop&w=1200&q=90",
                    "A modern sports-inspired sneaker combining a comfortable everyday fit with a contemporary athletic appearance. Designed for casual movement and active lifestyles.",
                    false
                )
            );

            for (Product product : products) {

                Product existingProduct =
                        productRepository
                                .findAll()
                                .stream()
                                .filter(existing ->
                                        existing.getName()
                                                .equalsIgnoreCase(product.getName()))
                                .findFirst()
                                .orElse(null);

                if (existingProduct != null) {

                    existingProduct.setBrand(product.getBrand());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setCategory(product.getCategory());
                    existingProduct.setColor(product.getColor());
                    existingProduct.setAvailableSizes(product.getAvailableSizes());
                    existingProduct.setStock(product.getStock());

                    existingProduct.setImageUrl(product.getImageUrl());

                    // Additional gallery images will be added
                    // once we have genuine alternate views.
                    existingProduct.setImageUrl2(null);
                    existingProduct.setImageUrl3(null);
                    existingProduct.setImageUrl4(null);
                    existingProduct.setImageUrl5(null);

                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setFeatured(product.getFeatured());

                    productRepository.save(existingProduct);

                } else {

                    productRepository.save(product);
                }
            }

            System.out.println(
                    "SoleMate product catalog initialized."
            );
        };
    }

    private Product createProduct(
            String name,
            String brand,
            Double price,
            String category,
            String color,
            String availableSizes,
            Integer stock,
            String imageUrl,
            String description,
            Boolean featured) {

        Product product = new Product(
                name,
                brand,
                price,
                category,
                color,
                availableSizes,
                stock,
                imageUrl,
                description,
                featured
        );

        return product;
    }
}