package com.example.adambackend.repository;

import com.example.adambackend.entities.TagProduct;
import com.example.adambackend.entities.TagProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TagProductRepository extends JpaRepository<TagProduct, TagProductPK> {

    @Query(value = "select tag_id from tag_products where product_id=?1 and  status = 0", nativeQuery = true)
    List<Integer> findTagIdByProductId(Integer productId);

    @Transactional
    @Modifying
    @Query(value = "update tag_products set status = 0 where tag_id=?1", nativeQuery = true)
    void updateDeletedTagId(Integer productId);

    @Transactional
    @Modifying
    @Query(value = "update tag_products set status = 0 where product_id=?1", nativeQuery = true)
    void updateDeletedByProductId(Integer productId);
}
