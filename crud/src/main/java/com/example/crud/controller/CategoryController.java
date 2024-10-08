package com.example.crud.controller;



import com.example.crud.entity.Category;
import com.example.crud.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    // 1. GET all categories with pagination
    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        
        Page<Category> categories = categoryService.getAllCategories(page, size);
        return ResponseEntity.ok(categories);
    }
    
    // 2. POST - create a new category
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        Category created = categoryService.createCategory(category);
        return ResponseEntity.ok(created);
    }
    
    // 3. GET category by Id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable(value = "id") Long id) {
        Category category = categoryService.getCategoryById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.status(404).body("Category not found with id: " + id);
        }
    }
    
    // 4. PUT - update category by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
        @PathVariable(value = "id") Long id, 
        @RequestBody Category categoryDetails) {
        
        Category updated = categoryService.updateCategory(id, categoryDetails);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(404).body("Category not found with id: " + id);
        }
    }
    
    // 5. DELETE - delete category by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id) {
        boolean isDeleted = categoryService.deleteCategory(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).body("Category not found with id: " + id);
        }
    }
}

