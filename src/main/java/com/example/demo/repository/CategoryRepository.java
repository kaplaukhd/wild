package com.example.demo.repository;

import com.example.demo.entities.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {


    @Query("SELECT DISTINCT c from categories c join Product p on c.id = p.subjectId")
    List<Category> getCategories();
}
