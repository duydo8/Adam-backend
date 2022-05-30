package com.example.adambackend.service.impl;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.repository.DetailOrderRepository;
import com.example.adambackend.service.DetailOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetailOrderServiceImpl implements DetailOrderService {
    @Autowired
    DetailOrderRepository detailOrderRepository;
    @Override
    public List<DetailOrder> findAll() {
        return detailOrderRepository.findAll();
    }

    @Override
    public DetailOrder save(DetailOrder detailOrder) {
        return detailOrderRepository.save(detailOrder);
    }

    @Override
    public void deleteById(Long id) {
    	detailOrderRepository.deleteById(id);
    }

    @Override
    public Optional<DetailOrder> findById(Long id) {
        return detailOrderRepository.findById(id);
    }
    @Override
    public List<DetailOrder> findAllByOrderId(Long orderId){
        return  detailOrderRepository.findAllByOrderId(orderId);
    }


}
