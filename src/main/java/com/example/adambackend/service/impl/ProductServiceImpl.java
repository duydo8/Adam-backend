package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Product;
import com.example.adambackend.repository.ProductRepository;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductSevice {
    @Autowired
    ProductRepository productRepository;
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findPage(int page, int size) {
        return productRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public Product save(Product Product) {
        return productRepository.save(Product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
}
