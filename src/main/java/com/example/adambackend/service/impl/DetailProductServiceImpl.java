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
    DetailProductRepository detailProductRepository;
    @Override
    public List<DetailProduct> findAll() {
        return detailProductRepository.findAll();
    }

    @Override
    public DetailProduct save(DetailProduct DetailProduct) {
        return detailProductRepository.save(DetailProduct);
    }

    @Override
    public void deleteById(Long id) {
        detailProductRepository.deleteById(id);
    }

    @Override
    public Optional<DetailProduct> findById(Long id) {
        return detailProductRepository.findById(id);
    }

    @Override
    public List<DetailProduct> findAllByProductId(Long idProduct){
        return detailProductRepository.findAllByProductId(idProduct);
    }


}
