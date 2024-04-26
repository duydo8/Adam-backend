package com.example.adambackend.service;

import com.example.adambackend.entities.Comment;
import com.example.adambackend.payload.comment.CommentDTO;

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

	Comment createCommentwithAccountIdAndProductId(String content, LocalDateTime localDateTime, Integer productId,
												   Integer accountId, Integer commentStatus, int vote);

	List<Comment> findAllCommentByProductId(Integer productId);

	List<Comment> findTop10CommentByProductId(Integer productId);

	Integer countCommentByProduct(Integer productId);

	void deleteByProductId(Integer productId);

	Comment update(CommentDTO commentDTO);

	String updateCommentDeleted(List<Integer> listCommentId);
}
