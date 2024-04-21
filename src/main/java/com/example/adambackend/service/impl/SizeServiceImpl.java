package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Size;
import com.example.adambackend.payload.size.SizeDTO;
import com.example.adambackend.repository.SizeRepository;
import com.example.adambackend.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    SizeRepository sizeRepository;

    @Override
    public List<Size> findAll(String name) {
        return sizeRepository.findAll(name);
    }

    @Override
    public Size save(Size Size) {
        if (CommonUtil.isNotNull(sizeRepository.findByName(Size.getSizeName()))) {
            return null;
        }
        return sizeRepository.save(Size);
    }

    @Override
    public void deleteById(Integer id) {
        sizeRepository.updateSizeDeleted(id);
    }

    @Override
    public Optional<Size> findById(Integer id) {
        return sizeRepository.findById(id);
    }

    @Override
    public Optional<Size> findByDetailProductId(Integer detailProductId) {
        return sizeRepository.findByDetailProductId(detailProductId);
    }

    @Override
    public Size save(SizeDTO sizeDTO) {
        Size size = sizeRepository.findByName(sizeDTO.getSizeName());
        if(CommonUtil.isNotNull(size)){
            return null;
        }
        size = new Size();
        size.setSizeName(sizeDTO.getSizeName());
        return sizeRepository.save(size);
    }

    @Override
    public Size findByName(String name) {
        return sizeRepository.findByName(name);
    }
}
