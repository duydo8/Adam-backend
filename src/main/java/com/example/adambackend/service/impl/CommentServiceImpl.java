package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Comment;
import com.example.adambackend.repository.CommentRepository;
import com.example.adambackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository CommentRepository;
    @Override
    public List<Comment> findAll() {
        return CommentRepository.findAll();
    }

    @Override
    public Comment create(Comment Comment) {
        return CommentRepository.save(Comment);
    }

    @Override
    public void deleteById(Long id) {
        CommentRepository.deleteById(id);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return CommentRepository.findById(id);
    }
}
