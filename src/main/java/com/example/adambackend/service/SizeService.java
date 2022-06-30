package com.example.adambackend.service;

import com.example.adambackend.entities.Size;

import java.util.List;
import java.util.Optional;

public interface SizeService {

    Optional<Size> findById(Integer id);

    void deleteById(Integer id);

    Size save(Size size);

    List<Size> findAll();

    Size findByDetailProductId(Integer detailProductId);
}
