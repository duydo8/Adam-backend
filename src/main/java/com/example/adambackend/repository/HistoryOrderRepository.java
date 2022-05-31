package com.example.adambackend.repository;

import com.example.adambackend.entities.HistoryOrder;
import com.example.adambackend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryOrderRepository extends JpaRepository<HistoryOrder, Long> {
    @Query("select hs from HistoryOrder  hs join Order o on o.")
    List<Order> findTop5HistoryOrderByCreateTime();
}
