package com.example.adambackend.service;

import com.example.adambackend.entities.HistoryOrder;

import java.util.List;

public interface HistoryOrderService {

	List<HistoryOrder> findByOrderId(Integer orderId);

	HistoryOrder save(HistoryOrder historyOrder);

	HistoryOrder findLastHistoryOrderByOrderId(Integer orderId);
}
