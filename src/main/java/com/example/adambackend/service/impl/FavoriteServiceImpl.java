package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Favorite;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleWebsite;
import com.example.adambackend.repository.FavoriteRepository;
import com.example.adambackend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteRepository favoriteRepository;

    @Override
    public void deleteByIdAccountAndProduct(Integer accountId, Integer productId) {
        favoriteRepository.deleteByIdAccountAndProduct(accountId, productId);
    }

    @Override
    public List<Favorite> findAll() {
        return favoriteRepository.findAll();
    }

    @Override
    public Favorite save(Favorite Favorite) {
        return favoriteRepository.save(Favorite);
    }


    @Override
    public Integer countFavoriteByAccountIdAndProductId(int idAccount, int idProduct) {
        return favoriteRepository.countFavoriteByAccountIdAndProductId(idAccount, idProduct);
    }

    @Override
    public List<ProductHandleWebsite> findProductFavoriteByAccountId(Integer id) {
        return favoriteRepository.findProductFavoriteByAccountId(id);
    }

    @Override
    public List<Integer> findTop10FavoriteProduct() {
        return favoriteRepository.findTop10FavoriteProductId();
    }

    @Override
    public ProductHandleWebsite findProductById(Integer id) {
        return favoriteRepository.findProductById(id);
    }

    @Override
    public Optional<Favorite> findByAccountIdAndProductId(Integer accountId, Integer productId) {
        return favoriteRepository.findByAccountIdAndProductId(accountId, productId);
    }

    @Override
    public void deleteFavoriteByAccountIdAndProductId(Integer accountId, Integer productId) {
        favoriteRepository.deleteFavoriteByAccountIdAndProductId(accountId, productId);
    }

    @Override
    public void deleteByProductId(Integer productId) {
        favoriteRepository.deleteByProductId(productId);
    }
}
