package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Ward;
import com.example.adambackend.repository.WardRepository;
import com.example.adambackend.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WardServiceImpl implements WardService {
    @Autowired
    WardRepository wardRepository;

    @Override
    public List<Ward> findAll() {
        return wardRepository.findAll();
    }

    @Override
    public Ward save(Ward ward) {
        return wardRepository.save(ward);
    }

    @Override
    public void deleteById(Long id) {
        wardRepository.deleteById(id);
    }

    @Override
    public Optional<Ward> findById(Long id) {
        return wardRepository.findById(id);
    }
}
