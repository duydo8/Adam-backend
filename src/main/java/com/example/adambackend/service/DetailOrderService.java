package com.example.adambackend.service;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.cart.CartItemResponse;
import com.example.adambackend.payload.detailOrder.DetailOrderAdmin;

import java.util.List;
import java.util.Optional;


public interface DetailOrderService {

    Optional<DetailOrder> findById(Integer id);
    void updateReason(String reason, Integer id);

    void deleteById(Integer id);
    List<DetailOrderAdmin> findByOrderId(Integer orderId);
    String findCodeById(Integer id);

    DetailOrder save(DetailOrder detailOrder);

    List<DetailOrder> findAll();
    DetailOrder findByCode(String code);


    List<DetailOrder> findAllByOrderId(Integer orderId);

    List<Product> findTop10ProductByCountQuantityInOrderDetail();

    void deleteAllByOrderId(Integer orderId);


    List<Integer> findProductIdByOrder();
}
