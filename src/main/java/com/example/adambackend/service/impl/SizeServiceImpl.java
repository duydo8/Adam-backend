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
    SizeRepository sizeRepository;

    @Override
    public List<Size> findAll() {
        return sizeRepository.findAll();
    }

    @Override
    public Size save(Size Size) {
        return sizeRepository.save(Size);
    }

    @Override
    public void deleteById(Integer id) {
        sizeRepository.deleteById(id);
    }

    @Override
    public Optional<Size> findById(Integer id) {
        return sizeRepository.findById(id);
    }

    @Override
    public Optional<Size> findByDetailProductId(Integer detailProductId) {
        return sizeRepository.findByDetailProductId(detailProductId);
    }
}
