package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.DetailOrder;


public interface DetailOrderService {

	Optional<DetailOrder> findById(Long id);

	void deleteById(Long id);

	DetailOrder save(DetailOrder detailOrder);

	List<DetailOrder> findAll();





}
