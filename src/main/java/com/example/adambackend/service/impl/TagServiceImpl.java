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
    private TagRepository tagRepository;

    @Override
    public List<Tag> findAll(String name) {
        return tagRepository.findAll(name);
    }

    @Override
    public Optional<Tag> findByTagName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }

    @Override
    public Tag save(Tag Tag) {
        return tagRepository.save(Tag);
    }

    @Override
    public void deleteById(Integer id) {
        tagRepository.updateDeletedByTagId(id);
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        return tagRepository.findById(id);
    }


}
