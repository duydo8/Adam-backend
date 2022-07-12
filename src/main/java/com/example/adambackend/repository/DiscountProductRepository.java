package com.example.adambackend.repository;

import com.example.adambackend.entities.DiscountProduct;
import com.example.adambackend.entities.HistoryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountProductRepository extends JpaRepository<DiscountProduct, Integer> {


}

