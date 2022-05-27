package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.HistoryOrder;

public interface HistoryOrderService {

	Optional<HistoryOrder> findById(Long id);

	void deleteById(Long id);

	HistoryOrder save(HistoryOrder historyOrder);

	List<HistoryOrder> findAll();
}
