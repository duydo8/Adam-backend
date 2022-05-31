package com.example.adambackend.service;

import com.example.adambackend.entities.Favorite;
import com.example.adambackend.entities.Product;

import java.util.List;
import java.util.Optional;

public interface FavoriteService {

    Optional<Favorite> findById(Long id);

    void deleteById(Long id);

    Favorite save(Favorite favorite);

    List<Favorite> findAll();

    Integer countFavoriteByAccountIdAndProductId(int idAccount, int idProduct);

    Product findProductFavoriteByAccountId(Long id);

    List<Product> findTop10FavoriteProduct();

    Favorite findByAccountIdAndProductId(Long accountId, Long productId);

    void deleteFavoriteByAccountIdAndProductId(Long accountId, Long productId);
}
