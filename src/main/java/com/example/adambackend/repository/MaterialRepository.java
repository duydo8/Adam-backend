package com.example.adambackend.repository;

import com.example.adambackend.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Transactional
    @Modifying
    @Query(value = "delete from material_products where product_id=?1",nativeQuery = true)
    void deleteByProductId(Integer productId);
}
