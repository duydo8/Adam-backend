package com.example.adambackend.service;

import com.example.adambackend.entities.Favorite;
import com.example.adambackend.entities.Product;

import java.util.List;
import java.util.Optional;

public interface FavoriteService {

    Optional<Favorite> findById(Integer id);

    void deleteById(Integer id);

    Favorite save(Favorite favorite);

    List<Favorite> findAll();

    Integer countFavoriteByAccountIdAndProductId(int idAccount, int idProduct);

    List<Product> findProductFavoriteByAccountId(Integer id);

    List<Product> findTop10FavoriteProduct();

    Favorite findByAccountIdAndProductId(Integer accountId, Integer productId);

    void deleteFavoriteByAccountIdAndProductId(Integer accountId, Integer productId);

    void deleteByProductId(Integer productId);
}
