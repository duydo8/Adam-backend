package com.example.adambackend.service;

import com.example.adambackend.entities.CartItems;

import java.util.List;
import java.util.Optional;

public interface CartItemService {

    Optional<CartItems> findById(Integer id);

    void deleteById(Integer id);

    CartItems save(CartItems cartItems);

    List<CartItems> findAll();
}
