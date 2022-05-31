package com.example.adambackend.service;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.Product;

import java.util.List;
import java.util.Optional;


public interface DetailOrderService {

    Optional<DetailOrder> findById(Long id);

    void deleteById(Long id);

    DetailOrder save(DetailOrder detailOrder);

    List<DetailOrder> findAll();


    List<DetailOrder> findAllByOrderId(Long orderId);

    List<Product> findTop10ProductByCountQuantityInOrderDetail();
}
