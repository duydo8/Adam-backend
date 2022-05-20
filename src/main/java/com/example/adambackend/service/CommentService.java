package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.Comment;

public interface CommentService {

	Optional<Comment> findById(Long id);

	void deleteById(Long id);

	Comment create(Comment Comment);

	List<Comment> findAll();
}
