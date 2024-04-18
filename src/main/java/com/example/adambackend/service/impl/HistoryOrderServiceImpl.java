package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.HistoryOrder;
import com.example.adambackend.repository.HistoryOrderRepository;
import com.example.adambackend.service.HistoryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryOrderServiceImpl implements HistoryOrderService {
	@Autowired
	private HistoryOrderRepository historyOrderRepository;

	@Override
	public List<HistoryOrder> findByOrderId(Integer orderId){
		if(CommonUtil.isNotNull(orderId)){
			return null;
		}
		return historyOrderRepository.findByOrderId(orderId);
	}

	@Override
	public HistoryOrder save(HistoryOrder historyOrder){
		return historyOrderRepository.save(historyOrder);
	}

	@Override
	public HistoryOrder findLastHistoryOrderByOrderId(Integer orderId){
		return historyOrderRepository.findLastHistoryOrderByOrderId(orderId);
	}


}
