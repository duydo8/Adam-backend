package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Material;
import com.example.adambackend.repository.MaterialRepository;
import com.example.adambackend.service.MaterialProductService;
import com.example.adambackend.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {
	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private MaterialProductService materialProductService;

	@Override
	public List<Material> findAll(String name) {
		return materialRepository.findAll(name);
	}

	@Override
	public Material save(Material material) {
		if (CommonUtil.isNotNull(materialRepository.findByName(material.getMaterialName()))) {
			return null;
		}
		return materialRepository.save(material);
	}

	@Override
	public void deleteById(Integer id) {
		materialRepository.updateDeleteById(id);
	}

	@Override
	public Optional<Material> findById(Integer id) {
		return materialRepository.findById(id);
	}

	@Override
	public void updateDeletedByListId(List<Integer> materialIs){
		if (materialIs.size() > 0) {
			for (Integer x : materialIs) {
				Optional<Material> materialOptional = materialRepository.findById(x);
				if (materialOptional.isPresent()) {
					materialProductService.updateMaterialProductsDeletedByMaterialId(x);
					materialRepository.updateDeleteById(x);
				}
			}
		}
	}
}
