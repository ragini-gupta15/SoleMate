package com.solemate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.solemate.entity.Product;
import com.solemate.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {

        return productRepository.findById(id).map(product -> {

            product.setName(updatedProduct.getName());
            product.setBrand(updatedProduct.getBrand());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());

            return productRepository.save(product);
        });
    }

    public boolean deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            return false;
        }

        productRepository.deleteById(id);
        return true;
    }
}