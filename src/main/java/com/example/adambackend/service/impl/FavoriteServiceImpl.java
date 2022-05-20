package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Favorite;
import com.example.adambackend.repository.FavoriteRepository;
import com.example.adambackend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteRepository FavoriteRepository;
    @Override
    public List<Favorite> findAll() {
        return FavoriteRepository.findAll();
    }

    @Override
    public Favorite create(Favorite Favorite) {
        return FavoriteRepository.save(Favorite);
    }

    @Override
    public void deleteById(Long id) {
        FavoriteRepository.deleteById(id);
    }

    @Override
    public Optional<Favorite> findById(Long id) {
        return FavoriteRepository.findById(id);
    }
}
