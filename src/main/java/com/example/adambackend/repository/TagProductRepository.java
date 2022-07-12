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
    @Transactional
    @Modifying
    @Query(value = "delete from tag_products where tag_id=?1", nativeQuery = true)
    Integer deleteByTagId(Integer tagId);

    @Query(value = "select tag_id from tag_products where product_id=?1 and  is_active=1 and is_deleted=0 ", nativeQuery = true)
    List<Integer> findTagIdByProductId(Integer productId);

    @Transactional
    @Modifying
    @Query(value = "update tag_products set is_active=0 and is_deleted=1 where tag_id=?1", nativeQuery = true)
    void updateDeletedTagId(Integer productId);
}
