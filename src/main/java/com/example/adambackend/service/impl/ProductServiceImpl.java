package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.product.CustomProductFilterRequest;
import com.example.adambackend.payload.product.ProductTop10Create;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleValue;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleWebsite;
import com.example.adambackend.repository.ProductRepository;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return productRepository.findAll(PageRequest.of(page, size, Sort.by("createDate").descending()));
    }

    @Override
    public Boolean checkFavorite(Integer productId, Integer accountId) {
        List<Integer> x = productRepository.checkFavorite(productId, accountId);
        if (x == null || x.size() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public Product save(Product Product) {
        return productRepository.save(Product);
    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductTop10Create> findTop10productByCreateDate() {
        return productRepository.findTop10productByCreateDate();
    }

    @Override
    public List<Product> findAllByTagName(String tagName) {
        return productRepository.findAllByTagName(tagName);
    }

    @Override
    public Page<CustomProductFilterRequest> findPageableByOption(Integer categoryId, Integer sizeId, Integer colorId, Integer materialId, Integer tagId,
                                                                 Double bottomPrice, Double topPrice, Pageable pageable) {
        return productRepository.findPageableByOption(categoryId, sizeId,
                colorId, materialId, tagId, bottomPrice, topPrice, pageable);
    }

    @Override
    public List<Product> findTop10ProductBestSale() {
        return productRepository.findTop10ProductBestSale();
    }

    @Override
    public Product findByDetailProductId(Integer detalId) {
        return productRepository.findByDetailProductId(detalId);
    }

    @Override
    public Optional<ProductHandleWebsite> findOptionWebsiteByAccountIdProductId(Integer productId, Integer accountId) {
        return productRepository.findOptionWebsiteByProductId(productId, accountId);
    }

    @Override
    public Optional<ProductHandleValue> findOptionWebsiteByProductId(Integer productId) {
        return productRepository.findOptionByProductId(productId);
    }
}
