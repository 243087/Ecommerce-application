package com.rahul.productservice.repository;

import com.rahul.productservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Category findByTitle(String title);
    Optional<Category> findById(Integer id);
}
