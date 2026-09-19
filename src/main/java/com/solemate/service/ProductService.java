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

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(
            Long id,
            Product updatedProduct) {

        validateProduct(updatedProduct);

        return productRepository.findById(id)
                .map(existingProduct -> {

                    existingProduct.setName(
                            updatedProduct.getName()
                    );

                    existingProduct.setBrand(
                            updatedProduct.getBrand()
                    );

                    existingProduct.setPrice(
                            updatedProduct.getPrice()
                    );

                    existingProduct.setCategory(
                            updatedProduct.getCategory()
                    );

                    existingProduct.setColor(
                            updatedProduct.getColor()
                    );

                    existingProduct.setAvailableSizes(
                            updatedProduct.getAvailableSizes()
                    );

                    existingProduct.setStock(
                            updatedProduct.getStock()
                    );

                    existingProduct.setImageUrl(
                            updatedProduct.getImageUrl()
                    );

                    existingProduct.setImageUrl2(
                            updatedProduct.getImageUrl2()
                    );

                    existingProduct.setImageUrl3(
                            updatedProduct.getImageUrl3()
                    );

                    existingProduct.setImageUrl4(
                            updatedProduct.getImageUrl4()
                    );

                    existingProduct.setImageUrl5(
                            updatedProduct.getImageUrl5()
                    );

                    existingProduct.setDescription(
                            updatedProduct.getDescription()
                    );

                    existingProduct.setFeatured(
                            updatedProduct.getFeatured()
                    );

                    return productRepository.save(
                            existingProduct
                    );
                });
    }

    public boolean deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            return false;
        }

        productRepository.deleteById(id);
        return true;
    }

    private void validateProduct(Product product) {

        if (product == null) {
            throw new IllegalArgumentException(
                    "Product data is required"
            );
        }

        if (product.getName() == null ||
                product.getName().isBlank()) {

            throw new IllegalArgumentException(
                    "Product name is required"
            );
        }

        if (product.getBrand() == null ||
                product.getBrand().isBlank()) {

            throw new IllegalArgumentException(
                    "Product brand is required"
            );
        }

        if (product.getPrice() == null ||
                product.getPrice() <= 0) {

            throw new IllegalArgumentException(
                    "Product price must be greater than 0"
            );
        }

        if (product.getStock() == null ||
                product.getStock() < 0) {

            throw new IllegalArgumentException(
                    "Product stock cannot be negative"
            );
        }

        if (product.getCategory() == null ||
                product.getCategory().isBlank()) {

            throw new IllegalArgumentException(
                    "Product category is required"
            );
        }
    }
}