package com.example.adambackend.service.impl;


import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.repository.DetailProductRepository;
import com.example.adambackend.service.DetailProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public void deleteById(Integer id) {
        detailProductRepository.deleteById(id);
    }

    @Override
    public Optional<DetailProduct> findById(Integer id) {
        return detailProductRepository.findById(id);
    }

    @Override
    public List<DetailProduct> findAllByProductId(Integer idProduct) {
        return detailProductRepository.findAllByProductId(idProduct);
    }
    @Override
    public void deleteByProductId(Integer productId){
        detailProductRepository.deleteByProductId(productId);
    }


}
