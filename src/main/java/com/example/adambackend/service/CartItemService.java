package com.example.adambackend.service;

import com.example.adambackend.entities.CartItems;

import java.util.List;
import java.util.Optional;

public interface CartItemService {

    Optional<CartItems> findById(Long id);

    void deleteById(Long id);

    CartItems save(CartItems cartItems);

    List<CartItems> findAll();
}
