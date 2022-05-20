package com.example.adambackend.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.repository.DetailProductRepository;
import com.example.adambackend.service.DetailProductService;

@Service
public class DetailProductServiceImpl implements DetailProductService {
    @Autowired
    DetailProductRepository DetailProductRepository;
    @Override
    public List<DetailProduct> findAll() {
        return DetailProductRepository.findAll();
    }

    @Override
    public DetailProduct create(DetailProduct DetailProduct) {
        return DetailProductRepository.save(DetailProduct);
    }

    @Override
    public void deleteById(Long id) {
        DetailProductRepository.deleteById(id);
    }

    @Override
    public Optional<DetailProduct> findById(Long id) {
        return DetailProductRepository.findById(id);
    }


}
