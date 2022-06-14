package com.example.adambackend.service;

import com.example.adambackend.entities.Comment;
import com.example.adambackend.enums.CommentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(Integer id);

    void deleteById(Integer id);

    Comment save(Comment comment);

    List<Comment> findAll();

    Integer countCommentByAccountIdAndProductId(Integer idAccount, Integer idProduct);

    List<Comment> findCommentByIdAccountAndIdProduct(Integer idAccount, Integer idProduct);

    Comment createAccountwithAccountIdAndProductId(String content, LocalDateTime localDateTime, Integer productId, Integer accountId, CommentStatus commentStatus,int vote);

    List<Comment> findAllCommentByProductIdAndStatusIsActive(Integer productId);

    List<Comment> findTop10CommentByProductId(Integer productId);
    Integer countCommentByProduct(Integer productId);
}
