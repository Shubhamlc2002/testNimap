package com.example.crud.controller;



import com.example.crud.entity.Product;
import com.example.crud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    // 1. GET all products with pagination
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        
        Page<Product> products = productService.getAllProducts(page, size);
        return ResponseEntity.ok(products);
    }
    
    // 2. POST - create a new product
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        Product created = productService.createProduct(product);
        if (created != null) {
            return ResponseEntity.ok(created);
        } else {
            return ResponseEntity.status(404).body("Category not found with id: " + product.getCategory().getId());
        }
    }
    
    // 3. GET product by Id (with category details)
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable(value = "id") Long id) {
        Product product = productService.getProductById(id).orElse(null);
        if (product != null) {
            // To ensure category is fetched
            product.getCategory().getName();
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(404).body("Product not found with id: " + id);
        }
    }
    
    // 4. PUT - update product by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
        @PathVariable(value = "id") Long id, 
        @RequestBody Product productDetails) {
        
        Product updated = productService.updateProduct(id, productDetails);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(404).body("Product not found with id: " + id);
        }
    }
    
    // 5. DELETE - delete product by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).body("Product not found with id: " + id);
        }
    }
}
