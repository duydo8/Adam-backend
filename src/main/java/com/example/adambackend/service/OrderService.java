package com.example.adambackend.service;

import com.example.adambackend.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();

    Order save(Order order);

    void deleteById(Integer id);

    Optional<Order> findById(Integer id);

    Page<Order> findAll(Pageable pageable);

    //    @Override
    //    public List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId) {
    //        return orderRepository.findTop5ByOrderLessThanOrderByCreateDateDesc(accountId);
    //    }
    List<Order> findByAccountId(Integer accountId, Integer status);

//    List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId);
}
