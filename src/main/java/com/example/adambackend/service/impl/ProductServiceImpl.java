package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Product;
import com.example.adambackend.repository.ProductRepository;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductSevice {
    @Autowired
    ProductRepository ProductRepository;
    @Override
    public List<Product> findAll() {
        return ProductRepository.findAll();
    }

    @Override
    public Product create(Product Product) {
        return ProductRepository.save(Product);
    }

    @Override
    public void deleteById(Long id) {
        ProductRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return ProductRepository.findById(id);
    }
}
