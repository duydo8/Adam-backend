package com.example.adambackend.service.impl;

import com.example.adambackend.repository.TagProductRepository;
import com.example.adambackend.service.TagProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagProductServiceImpl implements TagProductService {

	@Autowired
	private TagProductRepository tagProductRepository;

	@Override
	public void updateDeletedByProductId(Integer productId) {
		tagProductRepository.updateDeletedByProductId(productId);
	}
}
