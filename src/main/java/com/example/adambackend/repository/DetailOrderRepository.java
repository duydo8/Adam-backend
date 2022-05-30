package com.example.adambackend.repository;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.adambackend.entities.DetailOrder;

import java.util.List;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {
    @Query("select do from DetailOrder do join Order o on o.id=do.order.id where o.id=?1")
    List<DetailOrder> findAllByOrderId(Long orderId);
}
