package com.example.adambackend.service;

public interface MaterialProductService {


	void updateMaterialProductsDeletedByMaterialId(Integer materialId);

	void updateDeletedByProductId(Integer productId);
}
