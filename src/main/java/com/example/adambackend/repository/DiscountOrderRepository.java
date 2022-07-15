package com.example.adambackend.repository;

import com.example.adambackend.entities.DiscountOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface DiscountOrderRepository extends JpaRepository<DiscountOrder, Integer> {
    @Query("select dp from DiscountOrder dp where  dp.isActive=true and dp.isDeleted=false")
    List<DiscountOrder> findAll();

    @Transactional
    @Modifying
    @Query("update DiscountOrder  dp set dp.isActive=false , dp.isDeleted=true where dp.id=?1")
    void updateIsActive(Integer id);
}
