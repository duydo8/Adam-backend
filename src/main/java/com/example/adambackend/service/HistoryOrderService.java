package com.example.adambackend.service;

import com.example.adambackend.entities.HistoryOrder;

import java.util.List;
import java.util.Optional;

public interface HistoryOrderService {

    Optional<HistoryOrder> findById(Long id);

    void deleteById(Long id);

    HistoryOrder save(HistoryOrder historyOrder);

    List<HistoryOrder> findAll();
}
