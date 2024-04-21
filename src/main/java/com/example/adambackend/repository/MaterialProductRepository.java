package com.example.adambackend.repository;

import com.example.adambackend.entities.MaterialProduct;
import com.example.adambackend.entities.MaterialProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MaterialProductRepository extends JpaRepository<MaterialProduct, MaterialProductPK> {

    @Query(value = "select material_id from material_products where product_id=?1 and status = 1", nativeQuery = true)
    List<Integer> findMaterialIdByProductId(Integer productId);

    @Modifying
    @Transactional
    @Query(value = "update material_products set status =0 where  product_id= ?1", nativeQuery = true)
    void updateDeletedByProductId(Integer productId);

    @Modifying
    @Transactional
    @Query(value = "update material_products set status = 0 where material_id = ?1", nativeQuery = true)
    void updateMaterialProductsDeletedByMaterialId(Integer id);

    @Query(value = "select * from material_products where status = 1", nativeQuery = true)
    List<MaterialProduct> findAlls();

}
