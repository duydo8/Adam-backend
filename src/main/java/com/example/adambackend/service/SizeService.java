package com.example.adambackend.service;

import com.example.adambackend.entities.Size;
import com.example.adambackend.payload.size.SizeDTO;

import java.util.List;
import java.util.Optional;

public interface SizeService {

    Optional<Size> findById(Integer id);

    void deleteById(Integer id);

    Size save(Size size);

    List<Size> findAll(String name);

    Optional<Size> findByDetailProductId(Integer detailProductId);

    Size save(SizeDTO sizeDTO);

    Size findByName(String name);
}
