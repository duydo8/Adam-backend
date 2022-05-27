package com.example.adambackend.service;

import com.example.adambackend.entities.Discount;

import java.util.List;
import java.util.Optional;

public interface DiscountService {
    List<Discount> findAll();

    Discount save(Discount discount);

    void deleteById(Long id);

    Optional<Discount> findById(Long id);
}
