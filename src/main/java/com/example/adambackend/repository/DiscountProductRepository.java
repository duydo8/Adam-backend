package com.example.adambackend.repository;

import com.example.adambackend.entities.DiscountProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DiscountProductRepository extends JpaRepository<DiscountProduct, Integer> {
    @Query("select dp from DiscountProduct dp where  dp.isActive=true and dp.isDeleted=false")
    List<DiscountProduct> findAll();

    @Transactional
    @Modifying
    @Query("update DiscountProduct  dp set dp.isActive=false , dp.isDeleted=true where dp.id=?1")
    void updateIsActive(Integer id);

}

