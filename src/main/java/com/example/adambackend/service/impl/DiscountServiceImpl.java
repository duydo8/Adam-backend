package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Discount;
import com.example.adambackend.repository.DiscountRepository;
import com.example.adambackend.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    DiscountRepository discountRepository;
    @Override
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Override
    public Discount save(Discount discount) {
        return discountRepository.save(discount);
    }

    @Override
    public void deleteById(Long id) {
        discountRepository.deleteById(id);
    }

    @Override
    public Optional<Discount> findById(Long id) {
        return discountRepository.findById(id);
    }
}
