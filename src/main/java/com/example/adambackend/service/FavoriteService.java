package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.Favorite;

public interface FavoriteService {

	Optional<Favorite> findById(Long id);

	void deleteById(Long id);

	Favorite save(Favorite favorite);

	List<Favorite> findAll();

    Integer countFavoriteByAccountIdAndProductId(int idAccount, int idProduct);
}
