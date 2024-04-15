package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Order;
import com.example.adambackend.repository.OrderRepository;
import com.example.adambackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public List<Double> sumTotalPriceByTime(Integer year) {
        return orderRepository.sumTotalPriceByTime(year);
    }

    //    @Override
//    public List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId) {
//        return orderRepository.findTop5ByOrderLessThanOrderByCreateDateDesc(accountId);
//    }
    @Override
    public List<Order> findByAccountId(Integer accountId, Integer status) {
        return orderRepository.findOrderByAccountId(accountId, status);
    }

    @Override
    public Integer countAllOrderByTime(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.countAllOrderByTime(startDate, endDate);

    }

    @Override
    public Integer countCancelOrderByTime(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.countCancelOrderByTime(startDate, endDate);
    }

    @Override
    public Integer countsuccessOrderByTime(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.countSuccessOrderByTime(startDate, endDate);
    }

    @Override
    public List<Double> sumSuccessOrderByTime(Integer year) {
        return orderRepository.sumSuccessOrderByTime(year);
    }

    @Override
    public List<Double> sumCancelOrderByTime(Integer year) {
        return orderRepository.sumCancelOrderByTime(year);
    }

    @Override
    public List<Double> sumPaybackOrderByTime(Integer year) {
        return orderRepository.sumPaybackOrderByTime(year);
    }
}
