package com.example.adambackend.repository;

import com.example.adambackend.entities.MaterialProduct;
import com.example.adambackend.entities.MaterialProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MaterialProductRepository extends JpaRepository<MaterialProduct, MaterialProductPK> {
    @Transactional
    @Modifying
    @Query(value = "delete from material_products where material_id=?1",nativeQuery = true)
    Integer deleteByMateralId(Integer materialId);
}
