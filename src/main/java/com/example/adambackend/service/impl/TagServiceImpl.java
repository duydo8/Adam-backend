package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Tag;
import com.example.adambackend.repository.TagRepository;
import com.example.adambackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository TagRepository;
    @Override
    public List<Tag> findAll() {
        return TagRepository.findAll();
    }

    @Override
    public Tag create(Tag Tag) {
        return TagRepository.save(Tag);
    }

    @Override
    public void deleteById(Long id) {
        TagRepository.deleteById(id);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return TagRepository.findById(id);
    }
}
