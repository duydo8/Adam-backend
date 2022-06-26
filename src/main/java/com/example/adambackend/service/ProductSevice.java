package com.example.adambackend.service;

import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.CustomProductFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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


    List<CustomProductFilterRequest> findPageableByOption(int categoryId, int sizeId, int colorId, int materialId, int tagId,
                                                          double bottomPrice, double topPrice, Pageable pageable);

    List<Product> findTop10ProductBestSale();
}
