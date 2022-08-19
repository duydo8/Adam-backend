package com.example.adambackend.service;

import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.product.CustomProductFilterRequest;
import com.example.adambackend.payload.product.ProductTop10Create;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleValue;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleWebsite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductSevice {

    Optional<Product> findById(Integer id);

    void deleteById(Integer id);

    Boolean checkFavorite(Integer productId, Integer accountId);

    Product save(Product product);

    List<Product> findAll();

    Page<Product> findPage(int page, int size);

    List<ProductTop10Create> findTop10productByCreateDate();

    List<Product> findAllByTagName(String tagName);


    Page<CustomProductFilterRequest> findPageableByOption(Integer categoryId, Integer sizeId, Integer colorId, Integer materialId, Integer tagId,
                                                          Double bottomPrice, Double topPrice, Pageable pageable);

    List<Product> findTop10ProductBestSale();

    Product findByDetailProductId(Integer detalId);

    Optional<ProductHandleWebsite> findOptionWebsiteByAccountIdProductId(Integer productId, Integer accountId);

    Optional<ProductHandleValue> findOptionWebsiteByProductId(Integer productId);
}
