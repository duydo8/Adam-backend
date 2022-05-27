package com.example.adambackend.repository;

import com.example.adambackend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.id=c.categoryParentId")
    List<Category> findAllCategoryParentId();
}
