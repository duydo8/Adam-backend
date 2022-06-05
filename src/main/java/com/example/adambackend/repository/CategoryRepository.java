package com.example.adambackend.repository;

import com.example.adambackend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select distinct c1.id,c1.category_name,c1.is_deleted,c1.category_parent_id from Categories c1,categories c2 where " +
            " c1.id=c2.category_parent_id ", nativeQuery = true)
    List<Category> findAllCategoryParentId();
}
