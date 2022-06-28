package com.example.adambackend.repository;

import com.example.adambackend.entities.TagProduct;
import com.example.adambackend.entities.TagProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TagProductRepository extends JpaRepository<TagProduct, TagProductPK> {
    @Transactional
    @Modifying
    @Query(value = "delete from tag_products where tag_id=?1", nativeQuery = true)
    Integer deleteByTagId(Integer tagId);
}
