package com.example.adambackend.repository;

import com.example.adambackend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Long accountId);
}
