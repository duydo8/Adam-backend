package com.example.adambackend.repository;

import com.example.adambackend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "select DISTINCT c2.id,c2.category_name,c2.is_deleted,c2.create_date,c2.category_parent_id,c2.is_active               from categories c1, categories c2 \n" +
            "where c2.id= c1.category_parent_id or c2.category_parent_id is null and c2.is_deleted=0", nativeQuery = true)
    List<Category> findAllCategoryParentId();

    List<Category> findByCategoryParentId(int parentId);
}
