package com.example.adambackend.repository;

import com.example.adambackend.entities.HistoryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryOrderRepository extends JpaRepository<HistoryOrder, Integer> {
    @Query(value = "select ho from HistoryOrder  ho where ho.order.id=?1")
    List<HistoryOrder> findByOrderId(Integer orderId);
}
