package com.example.adambackend.repository;

import com.example.adambackend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

//    List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId);


}
