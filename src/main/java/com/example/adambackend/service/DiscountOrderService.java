package com.example.adambackend.service;

import com.example.adambackend.entities.DiscountOrder;
import com.example.adambackend.entities.Event;
import com.example.adambackend.payload.discountOrder.DiscountOrderDTO;

import java.util.List;
import java.util.Optional;

public interface DiscountOrderService {

	Optional<DiscountOrder> findById(Integer id);

	DiscountOrder update(DiscountOrderDTO discountOrderUpdate);

	void updateStatusById(Integer status, Integer id);

	DiscountOrder save(DiscountOrder discountOrder);

	List<DiscountOrder> findAll(String name);

	List<DiscountOrder> findByEventId(Integer eventId);

	String validateCreateDisccountOrder(Event event, DiscountOrderDTO discountOrderDTO);

	DiscountOrder createDiscountOrder(Event event, DiscountOrderDTO discountOrderDTO);

	void updateDiscountOrder(DiscountOrder discountOrder, DiscountOrderDTO discountOrderDTO);

	List<DiscountOrder> findByTotalPriceAndTime(Double price, Integer eventId);

	DiscountOrder getById(Integer id);
}
