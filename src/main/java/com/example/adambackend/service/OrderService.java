package com.example.adambackend.service;

import com.example.adambackend.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();

    Order save(Order order);

    void deleteById(Integer id);

    Optional<Order> findById(Integer id);

//    List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId);
}
