package com.example.adambackend.service;

import com.example.adambackend.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductSevice {

    Optional<Product> findById(Integer id);

    void deleteById(Integer id);

    Product save(Product product);

    List<Product> findAll();

    Page<Product> findPage(int page, int size);

    List<Product> findTop10productByCreateDate();

   List<Product> findAllByTagName(String tagName);
    List<Product> findByColorSizePriceBrandAndMaterial(String colorName, String sizeName, String brand, String material, double bottomPrice, double topPrice);
}
