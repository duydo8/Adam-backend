package com.example.adambackend.service;

import com.example.adambackend.entities.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    Optional<Tag> findById(Long id);

    void deleteById(Long id);

    Tag save(Tag tag);

    List<Tag> findAll();
}
