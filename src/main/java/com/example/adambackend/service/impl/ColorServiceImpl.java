package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Color;
import com.example.adambackend.payload.color.ColorUpdate;
import com.example.adambackend.repository.ColorRepository;
import com.example.adambackend.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {

	@Autowired
	private ColorRepository colorRepository;

	@Override
	public List<Color> findAll(String name) {
		if (CommonUtil.isNotNull(name)) {
			return colorRepository.findAll(name);
		}
		return colorRepository.findAll();
	}

	@Override
	public Color save(Color color) {
		if (CommonUtil.isNotNull(colorRepository.findByName(color.getColorName()))) {
			return null;
		}
		return colorRepository.save(color);
	}

	@Override
	public Color update(ColorUpdate color) {
		Optional<Color> colorOptional = colorRepository.findById(color.getId());
		if (colorOptional.isPresent()) {
			if (CommonUtil.isNotNull(colorRepository.findByName(color.getColorName()))) {
				return null;
			}
			colorOptional.get().setColorName(color.getColorName());
			colorOptional.get().setStatus(color.getStatus());
			return colorOptional.get();
		}
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		colorRepository.deleteById(id);
	}

	@Override
	public Optional<Color> findById(Integer id) {
		return colorRepository.findById(id);
	}

	@Override
	public Optional<Color> findByDetailProductId(Integer detailProductId) {
		return colorRepository.findByDetailProductId(detailProductId);
	}

	@Override
	public String updateColorsDeleted(List<Integer> listColorIdDTO) {
		System.out.println(listColorIdDTO.size());
		if (!listColorIdDTO.isEmpty()) {
			for (Integer id : listColorIdDTO) {
				Optional<Color> colorOptional = colorRepository.findById(id);
				if (colorOptional.isPresent()) {
					colorRepository.updateColorsDeleted(id);
				}
			}
			return "success";
		}
		return "not found";
	}
}
