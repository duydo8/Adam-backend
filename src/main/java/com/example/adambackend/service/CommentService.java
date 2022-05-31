package com.example.adambackend.service;

import com.example.adambackend.entities.Comment;
import com.example.adambackend.enums.CommentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(Long id);

    void deleteById(Long id);

    Comment save(Comment comment);

    List<Comment> findAll();

    Integer countCommentByAccountIdAndProductId(Long idAccount, Long idProduct);

    List<Comment> findCommentByIdAccountAndIdProduct(Long idAccount, Long idProduct);

    Comment createAccountwithAccountIdAndProductId(String content, LocalDateTime localDateTime, Long productId, Long accountId, CommentStatus commentStatus);

    List<Comment> findAllCommentByProductIdAndStatusIsActive(Long productId);

    List<Comment> findTop10CommentByProductId(Long productId);
}
