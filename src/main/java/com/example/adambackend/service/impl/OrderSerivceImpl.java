package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Order;
import com.example.adambackend.security.jwtConfig.repository.OrderRepository;
import com.example.adambackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderSerivceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteById(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

//    @Override
//    public List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId) {
//        return orderRepository.findTop5ByOrderLessThanOrderByCreateDateDesc(accountId);
//    }

}
