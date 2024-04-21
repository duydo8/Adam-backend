package com.example.adambackend.service;

import com.example.adambackend.entities.TagProduct;

import java.util.List;

public interface TagProductService {

	void updateDeletedByProductId(Integer productId);

	TagProduct save(TagProduct tagProduct);

	List<Integer> findTagIdByProductId(Integer productId);

	void updateDeletedByTagId(Integer tagId);
}
