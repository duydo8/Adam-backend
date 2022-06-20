package com.example.adambackend.service.impl;


import com.example.adambackend.entities.Color;
import com.example.adambackend.security.jwtConfig.repository.ColorRepository;
import com.example.adambackend.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    ColorRepository colorRepository;

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public Color save(Color Color) {
        return colorRepository.save(Color);
    }

    @Override
    public void deleteById(Integer id) {
        colorRepository.deleteById(id);
    }

    @Override
    public Optional<Color> findById(Integer id) {
        return colorRepository.findById(id);
    }
}
