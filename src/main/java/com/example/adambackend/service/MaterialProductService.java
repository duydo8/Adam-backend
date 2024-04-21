package com.example.adambackend.service;

import com.example.adambackend.entities.MaterialProduct;

import java.util.List;

public interface MaterialProductService {


	void updateMaterialProductsDeletedByMaterialId(Integer materialId);

	void updateDeletedByProductId(Integer productId);

	MaterialProduct save(MaterialProduct materialProduct);

	List<Integer> findMaterialIdByProductId(Integer productId);
}
