package com.example.adambackend.repository;

import com.example.adambackend.entities.Category;
import com.example.adambackend.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select DISTINCT c2.id,c2.category_name,c2.is_deleted,c2.create_date,c2.category_parent_id,c2.is_active from categories c1, categories c2 \n" +
            "where c2.id= c1.category_parent_id or c2.category_parent_id is null or c1.category_parent_id is null and c2.is_deleted=0 and c2.is_active=1 and c1.is_deleted=0 and c1.is_active=1", nativeQuery = true)
    List<Category> findAllCategoryParentId();

    @Query(value = "select * from categories where category_parent_id=?1 and is_active=1 and is_deleted=0", nativeQuery = true)
    List<Category> findByCategoryParentId(int parentId);

    @Modifying
    @Transactional
    @Query(value = "update categories set is_deleted=1 , is_active=0 where id=?1", nativeQuery = true)
    void updateCategoriesDeleted(Integer id);

    @Query(value = "select * from categories where is_active=1 and is_deleted=0", nativeQuery = true)
    List<Category> findAll();
    @Query(value = "select c from Category c where c.isActive=true and c.isDeleted=false and c.categoryName like concat('%',:name,'%') ")
    List<Category> findAll(@Param("name")  String name);
}
