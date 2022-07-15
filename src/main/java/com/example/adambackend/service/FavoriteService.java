package com.example.adambackend.service;

import com.example.adambackend.entities.Favorite;
import com.example.adambackend.entities.FavoriteId;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleWebsite;

import java.util.List;
import java.util.Optional;

public interface FavoriteService {

    void deleteByIdAccountAndProduct(Integer accountId,Integer productId);

    Favorite save(Favorite favorite);

    List<Favorite> findAll();

    Integer countFavoriteByAccountIdAndProductId(int idAccount, int idProduct);

    List<ProductHandleWebsite> findProductFavoriteByAccountId(Integer id);

    List<Integer> findTop10FavoriteProduct();

    ProductHandleWebsite findProductById(Integer id);

    Optional<Favorite> findByAccountIdAndProductId(Integer accountId, Integer productId);

    void deleteFavoriteByAccountIdAndProductId(Integer accountId, Integer productId);

    void deleteByProductId(Integer productId);
}
