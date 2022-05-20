package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Size;
import com.example.adambackend.repository.SizeRepository;
import com.example.adambackend.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    SizeRepository SizeRepository;
    @Override
    public List<Size> findAll() {
        return SizeRepository.findAll();
    }

    @Override
    public Size create(Size Size) {
        return SizeRepository.save(Size);
    }

    @Override
    public void deleteById(Long id) {
        SizeRepository.deleteById(id);
    }

    @Override
    public Optional<Size> findById(Long id) {
        return SizeRepository.findById(id);
    }
}
