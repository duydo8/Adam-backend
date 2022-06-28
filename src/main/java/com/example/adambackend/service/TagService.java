package com.example.adambackend.service;

import com.example.adambackend.entities.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    Optional<Tag> findById(Integer id);

    void deleteById(Integer id);

    Tag save(Tag tag);

    List<Tag> findAll();

    Optional<Tag> findByTagName(String tagName);

    void deleteByProductId(Integer productId);
}
