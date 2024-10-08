package com.example.crud.service;

import com.example.crud.entity.Product;
import com.example.crud.repository.ProductRepository;
import com.example.crud.repository.CategoryRepository;
import com.example.crud.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return productRepository.findAll(pageable);
    }
    
    public Product createProduct(Product product) {
        // Ensure category exists
        Long categoryId = product.getCategory().getId();
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            product.setCategory(optionalCategory.get());
            return productRepository.save(product);
        } else {
            return null;
        }
    }
    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            
            // Update category if provided
            if (productDetails.getCategory() != null) {
                Long categoryId = productDetails.getCategory().getId();
                Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
                if (optionalCategory.isPresent()) {
                    product.setCategory(optionalCategory.get());
                } else {
                    // If category not found, you might choose to handle it differently
                    product.setCategory(null);
                }
            }
            
            return productRepository.save(product);
        } else {
            return null;
        }
    }
    
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
            return true;
        } else {
            return false;
        }
    }
}

