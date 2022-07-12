package com.example.adambackend.repository;

import com.example.adambackend.entities.DiscountOrder;
import com.example.adambackend.entities.HistoryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiscountOrderRepository extends JpaRepository<DiscountOrder, Integer> {
}
