package com.example.adambackend.service;

import com.example.adambackend.entities.DetailProduct;

import java.util.List;
import java.util.Optional;

public interface DetailProductService {

    Optional<DetailProduct> findById(Long id);

    void deleteById(Long id);

    DetailProduct save(DetailProduct detailProduct);

    List<DetailProduct> findAll();

    List<DetailProduct> findAllByProductId(Long idProduct);
}
