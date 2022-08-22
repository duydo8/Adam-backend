package com.example.adambackend.service;

import com.example.adambackend.entities.CartItems;
import com.example.adambackend.payload.cart.CartItemResponse;

import java.util.List;
import java.util.Optional;

public interface CartItemService {

    Optional<CartItems> findById(Integer id);

    List<CartItemResponse> findByOrderId(Integer orderId);

    void deleteById(Integer id);

    CartItems save(CartItems cartItems);

    List<CartItems> findAll();

    List<CartItems> findByAccountId(Integer accountId);

    void updateIsActive(Integer id);
}
