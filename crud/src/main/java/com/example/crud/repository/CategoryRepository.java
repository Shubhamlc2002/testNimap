package com.example.crud.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crud.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    

}
