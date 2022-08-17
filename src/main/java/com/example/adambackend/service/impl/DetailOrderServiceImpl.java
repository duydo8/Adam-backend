package com.example.adambackend.service.impl;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.detailOrder.DetailOrderAdmin;
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
    public DetailOrder findByCode(String code) {
        return detailOrderRepository.findByCode(code);
    }

    @Override
    public DetailOrder save(DetailOrder detailOrder) {
        return detailOrderRepository.save(detailOrder);
    }

    @Override
    public void deleteById(Integer id) {
        detailOrderRepository.deleteById(id);
    }

    @Override
    public List<DetailOrderAdmin> findByOrderId(Integer orderId) {
        return detailOrderRepository.findByOrderId(orderId);
    }

    @Override
    public String findCodeById(Integer id) {
        return detailOrderRepository.findCodeById(id);
    }

    @Override
    public Optional<DetailOrder> findById(Integer id) {
        return detailOrderRepository.findById(id);
    }

    @Override
    public void updateReason(String reason, Integer id) {
        detailOrderRepository.updateReason(reason, id);
    }

    @Override
    public List<DetailOrder> findAllByOrderId(Integer orderId) {
        return detailOrderRepository.findAllByOrderId(orderId);
    }

    @Override
    public List<Product> findTop10ProductByCountQuantityInOrderDetail() {
        return detailOrderRepository.findTop10ProductByCountQuantityInOrderDetail();
    }

    @Override
    public void deleteAllByOrderId(Integer orderId) {
        detailOrderRepository.deleteAllByOrderId(orderId);
    }

    @Override
    public List<Integer> findProductIdByOrder() {
        return detailOrderRepository.findProductIdByOrder();
    }
}
