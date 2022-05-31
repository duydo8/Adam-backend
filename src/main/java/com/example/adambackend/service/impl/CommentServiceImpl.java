package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Comment;
import com.example.adambackend.enums.CommentStatus;
import com.example.adambackend.repository.CommentRepository;
import com.example.adambackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment save(Comment Comment) {
        return commentRepository.save(Comment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Integer countCommentByAccountIdAndProductId(Long idAccount, Long idProduct) {
        return commentRepository.countCommentByAccountIdAndProductId(idAccount, idProduct);
    }

    @Override
    public List<Comment> findCommentByIdAccountAndIdProduct(Long idAccount, Long idProduct) {
        return commentRepository.findCommentByIdAccountAndIdProduct(idAccount, idProduct);
    }

    @Override
    public Comment createAccountwithAccountIdAndProductId(String content, LocalDateTime localDateTime, Long productId, Long accountId, CommentStatus commentStatus) {

        return commentRepository.createAccountwithAccountIdAndProductId(content, localDateTime, productId, accountId, commentStatus);
    }

    @Override
    public List<Comment> findAllCommentByProductIdAndStatusIsActive(Long productId) {
        return commentRepository.findAllCommentByProductIdAndStatusIsActive(productId);

    }

    @Override
    public List<Comment> findTop10CommentByProductId(Long productId) {
        return commentRepository.findTop10CommentByProductId(productId);
    }
}
