package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.Tag;

public interface TagService {

	Optional<Tag> findById(Long id);

	void deleteById(Long id);

	Tag save(Tag tag);

	List<Tag> findAll();
}
