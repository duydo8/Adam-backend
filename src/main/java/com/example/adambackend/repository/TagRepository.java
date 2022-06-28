package com.example.adambackend.repository;

import com.example.adambackend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByTagName(String tagName);
    @Transactional
    @Modifying
    @Query(value = "delete from tag_products where product_id=?1",nativeQuery = true)
    void deleteByProductId(Integer productId);
}
