package com.solemate.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

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
                    "/images/products/ultraboost/1.jpg",
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
                    "/images/products/air-force-1-07/1.jpg", 
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
                    "/images/products/forum-low/1.jpg",
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
                    "/images/products/dunk-low-retro/1.jpg",
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
                    "/images/products/air-jordan-1-low/1.jpg",
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
                    "/images/products/lebron-witness/1.jpg",
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
                    "/images/products/metcon-9/1.jpg",
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
                    "/images/products/nano-x4/1.jpg",
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
                    "/images/products/574-core/1.jpg",
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
                    "/images/products/old-skool/1.jpg",
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
                    "/images/products/chuck-70/1.jpg",
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
                    "/images/products/gel-kayano-30/1.jpg",
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
                    "/images/products/pegasus-41/1.jpg",
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
                    "/images/products/574-sport/1.jpg",
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

    String localImagePath =
            "/images/products/"
                    + createImageFolderName(name)
                    + "/1.jpg";

    String resolvedImageUrl = imageUrl;

    try {
        ClassPathResource imageResource =
                new ClassPathResource(localImagePath.substring(1));

        if (imageResource.exists()) {
            resolvedImageUrl = localImagePath;
        }
    } catch (Exception e) {
        System.out.println(
                "Could not check local image for " + name
        );
    }

    Product product = new Product(
            name,
            brand,
            price,
            category,
            color,
            availableSizes,
            stock,
            resolvedImageUrl,
            description,
            featured
    );

    return product;
    
}
private String createImageFolderName(String productName) {

    return productName
            .toLowerCase()
            .replaceAll("[^a-z0-9]+", "-")
            .replaceAll("^-|-$", "");
}
}