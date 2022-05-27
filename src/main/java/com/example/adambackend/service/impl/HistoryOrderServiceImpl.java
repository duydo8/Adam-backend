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
    HistoryOrderRepository historyOrderRepository;
    @Override
    public List<HistoryOrder> findAll() {
        return historyOrderRepository.findAll();
    }

    @Override
    public HistoryOrder save(HistoryOrder HistoryOrder) {
        return historyOrderRepository.save(HistoryOrder);
    }

    @Override
    public void deleteById(Long id) {
        historyOrderRepository.deleteById(id);
    }

    @Override
    public Optional<HistoryOrder> findById(Long id) {
        return historyOrderRepository.findById(id);
    }
}
