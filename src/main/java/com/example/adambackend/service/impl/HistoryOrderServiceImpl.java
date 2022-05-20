package com.example.adambackend.service.impl;

import com.example.adambackend.entities.HistoryOrder;
import com.example.adambackend.entities.HistoryOrder;
import com.example.adambackend.repository.HistoryOrderRepository;
import com.example.adambackend.service.HistoryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryOrderServiceImpl implements HistoryOrderService {
    @Autowired
    HistoryOrderRepository HistoryOrderRepository;
    @Override
    public List<HistoryOrder> findAll() {
        return HistoryOrderRepository.findAll();
    }

    @Override
    public HistoryOrder create(HistoryOrder HistoryOrder) {
        return HistoryOrderRepository.save(HistoryOrder);
    }

    @Override
    public void deleteById(Long id) {
        HistoryOrderRepository.deleteById(id);
    }

    @Override
    public Optional<HistoryOrder> findById(Long id) {
        return HistoryOrderRepository.findById(id);
    }
}
