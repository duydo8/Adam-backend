package com.example.adambackend.repository;

import com.example.adambackend.entities.TagProduct;
import com.example.adambackend.entities.TagProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagProductRepository extends JpaRepository<TagProduct, TagProductPK> {
}
