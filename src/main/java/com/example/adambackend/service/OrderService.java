package com.example.adambackend.service;

import com.example.adambackend.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();

    Order save(Order order);

    void deleteById(Long id);

    Optional<Order> findById(Long id);

    List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Long accountId);
}
