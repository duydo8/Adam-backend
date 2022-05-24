package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Color;
import com.example.adambackend.repository.ColorRepository;
import com.example.adambackend.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    com.example.adambackend.repository.ColorRepository ColorRepository;
    @Override
    public List<Color> findAll() {
        return ColorRepository.findAll();
    }

    @Override
    public Color create(Color Color) {
        return ColorRepository.save(Color);
    }

    @Override
    public void deleteById(Long id) {
        ColorRepository.deleteById(id);
    }

    @Override
    public Optional<Color> findById(Long id) {
        return ColorRepository.findById(id);
    }
}
