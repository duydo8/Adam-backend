package com.example.adambackend.service.impl;

import com.example.adambackend.entities.TagProduct;
import com.example.adambackend.repository.TagProductRepository;
import com.example.adambackend.service.TagProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagProductServiceImpl implements TagProductService {

	@Autowired
	private TagProductRepository tagProductRepository;

	@Override
	public void updateDeletedByTagId(Integer tagId) {
		tagProductRepository.updateDeletedByTagId(tagId);
	}

	@Override
	public void updateDeletedByProductId(Integer productId) {
		tagProductRepository.updateDeletedByProductId(productId);
	}

	@Override
	public TagProduct save(TagProduct tagProduct) {
		return tagProductRepository.save(tagProduct);
	}

	@Override
	public List<Integer> findTagIdByProductId(Integer productId){
		return tagProductRepository.findTagIdByProductId(productId);
	}
}
