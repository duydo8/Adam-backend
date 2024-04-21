package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.MaterialProduct;
import com.example.adambackend.repository.MaterialProductRepository;
import com.example.adambackend.service.MaterialProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialProductImpl implements MaterialProductService {

	@Autowired
	private MaterialProductRepository materialProductRepository;

	@Override
	public void updateMaterialProductsDeletedByMaterialId(Integer materialId) {
		if (CommonUtil.isNotNull(materialId)) {
			materialProductRepository.updateMaterialProductsDeletedByMaterialId(materialId);
		}
	}

	@Override
	public void updateDeletedByProductId(Integer productId) {
		materialProductRepository.updateDeletedByProductId(productId);
	}

	@Override
	public MaterialProduct save(MaterialProduct materialProduct) {
		return materialProductRepository.save(materialProduct);
	}

	@Override
	public List<Integer> findMaterialIdByProductId(Integer productId) {
		return materialProductRepository.findMaterialIdByProductId(productId);
	}
}
