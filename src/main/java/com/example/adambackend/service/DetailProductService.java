package com.example.adambackend.service;

import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.payload.detailProduct.DetailProductDTO;
import com.example.adambackend.payload.detailProduct.DetailProductRequest;
import com.example.adambackend.payload.detailProduct.DetailProductUpdateAdmin;
import com.example.adambackend.payload.detailProduct.NewDetailProductDTO;

import java.util.List;
import java.util.Optional;

public interface DetailProductService {

    Optional<DetailProduct> findById(Integer id);

    void deleteById(Integer id);

    DetailProduct save(DetailProduct detailProduct);

    List<DetailProduct> findAll();

    List<DetailProduct> findAllByProductId(Integer idProduct);

    void deleteByProductId(Integer productId);

    DetailProduct createDetailProduct(DetailProductDTO detailProductDTO);

    DetailProduct updateDetailProduct(DetailProductUpdateAdmin detailProduct);

    boolean deleteDetailProduct(Integer productId, Integer detailProductId);

    List<DetailProduct> createListDetailProductByOption(DetailProductRequest detailProductRequest);

    List<DetailProduct> updateListDetailProductAfterCreate(List<NewDetailProductDTO> newDetailProductDTOList);

    List<DetailProduct> updateArrayOptionValueDetailProduct(DetailProductRequest detailProductRequest);

    String deleteByListId(List<Integer> listDetailProductId);
}
