package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.DiscountOrder;
import com.example.adambackend.entities.Event;
import com.example.adambackend.payload.discountOrder.DiscountOrderDTO;
import com.example.adambackend.repository.DiscountOrderRepository;
import com.example.adambackend.service.DiscountOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountOrderServiceImpl implements DiscountOrderService {

	@Autowired
	private DiscountOrderRepository discountOrderRepository;

	@Override
	public Optional<DiscountOrder> findById(Integer id) {
		return discountOrderRepository.findById(id);
	}

	@Override
	public DiscountOrder update(DiscountOrderDTO discountOrderUpdate) {
		return null;
	}

	@Override
	public void updateStatusById(Integer status, Integer id) {
		discountOrderRepository.updateStatus(status, id);
	}

	@Override
	public DiscountOrder save(DiscountOrder discountOrder) {
		return discountOrderRepository.save(discountOrder);
	}

	@Override
	public List<DiscountOrder> findAll(String name) {
		return discountOrderRepository.findAll(name);
	}

	@Override
	public List<DiscountOrder> findByEventId(Integer eventId) {
		return discountOrderRepository.findByEventId(eventId);
	}

	@Override
	public String validateCreateDisccountOrder(Event event, DiscountOrderDTO discountOrderDTO) {
		if (CommonUtil.isNotNull(event)) {
			return "Event is not found";
		}
		if (discountOrderDTO.getOrderMaxRange() < discountOrderDTO.getOrderMinRange()) {
			return "OrderMaxRange must larger than OrderMinRange";
		}
		if (event.getType() && discountOrderDTO.getSalePrice() < 1) {
			return "This discount must be reduced by Amount";
		}
		if (!event.getType() && discountOrderDTO.getSalePrice() > 1) {
			return "This discount must be reduced by percent (salePrice < 1)";
		}
		return "";
	}

	@Override
	public DiscountOrder createDiscountOrder(Event event, DiscountOrderDTO discountOrderDTO) {
		DiscountOrder discountOrder = new DiscountOrder();

		discountOrder.setOrderMinRange(discountOrderDTO.getOrderMinRange());
		discountOrder.setOrderMaxRange(discountOrderDTO.getOrderMaxRange());
		discountOrder.setDiscountName(discountOrderDTO.getDiscountName());
		discountOrder.setCreateDate(LocalDateTime.now());
		discountOrder.setDescription(discountOrderDTO.getDescription());
		discountOrder.setStatus(1);
		discountOrder.setStartTime(discountOrderDTO.getStartTime());
		discountOrder.setSalePrice(discountOrderDTO.getSalePrice());
		discountOrder.setEndTime(discountOrderDTO.getEndTime());
		discountOrder.setEvent(event);
		discountOrder = discountOrderRepository.save(discountOrder);

		return discountOrder;
	}
}
