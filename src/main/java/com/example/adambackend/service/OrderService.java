package com.example.adambackend.service;

import com.example.adambackend.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();

    Order save(Order order);

    void deleteById(Integer id);

    Optional<Order> findById(Integer id);

    Page<Order> findAll(Pageable pageable);

    List<Double> sumTotalPriceByTime(Integer month);

    //    @Override
    //    public List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId) {
    //        return orderRepository.findTop5ByOrderLessThanOrderByCreateDateDesc(accountId);
    //    }
    List<Order> findByAccountId(Integer accountId, Integer status);

    Integer countAllOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    Integer countCancelOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    Integer countsuccessOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    List<Double> sumSuccessOrderByTime(Integer month);

    List<Double> sumCancelOrderByTime(Integer month);

    List<Double> sumPaybackOrderByTime(Integer month);

//    List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId);
}
