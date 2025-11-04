package com.example.productcatalogservice_nov2024.repos;

import com.example.productcatalogservice_nov2024.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Long> {


    Optional<Category> findById(Long aLong);
//    Optional<Category> findAll();
}
