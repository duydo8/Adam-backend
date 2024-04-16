package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Comment;
import com.example.adambackend.payload.comment.CommentDTO;
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
	private CommentRepository commentRepository;

	@Override
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	public Comment save(Comment Comment) {
		return commentRepository.save(Comment);
	}

	@Override
	public void deleteById(Integer id) {
		commentRepository.updateDeletedById(id);
		commentRepository.updateDeletedByParenId(id);
	}

	@Override
	public Optional<Comment> findById(Integer id) {
		return commentRepository.findById(id);
	}

	@Override
	public Integer countCommentByAccountIdAndProductId(Integer idAccount, Integer idProduct) {
		return commentRepository.countCommentByAccountIdAndProductId(idAccount, idProduct);
	}

	@Override
	public List<Comment> findCommentByIdAccountAndIdProduct(Integer idAccount, Integer idProduct) {
		return commentRepository.findCommentByIdAccountAndIdProduct(idAccount, idProduct);
	}

	@Override
	public Comment createCommentwithAccountIdAndProductId(String content, LocalDateTime localDateTime, Integer productId,
														  Integer accountId, Integer commentStatus, int vote) {
		return commentRepository.createCommentwithAccountIdAndProductId(content, localDateTime, productId, accountId, commentStatus, vote);
	}

	@Override
	public List<Comment> findAllCommentByProductIdAndStatusIsActive(Integer productId) {
		return commentRepository.findAllCommentByProductIdAndStatusIsActive(productId);

	}

	@Override
	public List<Comment> findTop10CommentByProductId(Integer productId) {
		return commentRepository.findTop10CommentByProductId(productId);
	}

	@Override
	public Integer countCommentByProduct(Integer productId) {
		return commentRepository.countCommentByProduct(productId);
	}

	@Override
	public void deleteByProductId(Integer productId) {
		commentRepository.deleteByProductId(productId);
	}

	@Override
	public Comment update(CommentDTO commentDTO) {
		Optional<Comment> commentOptional = commentRepository.findById(commentDTO.getId());
		if (commentOptional.isPresent()) {
			commentOptional.get().setVote(commentDTO.getVote());
			commentOptional.get().setCommentStatus(commentDTO.getCommentStatus());
			commentOptional.get().setContent(commentDTO.getContent());
			return commentOptional.get();
		}
		return null;
	}

	@Override
	public String updateCommentDeleted(List<Integer> listCommentId) {
		System.out.println(listCommentId.size());
		if (!listCommentId.isEmpty()) {
			for (Integer id : listCommentId) {
				Optional<Comment> comment = commentRepository.findById(id);
				if (comment.isPresent()) {
					commentRepository.updateDeletedById(id);
					commentRepository.updateDeletedByParenId(id);
				}
			}
			return "success";
		}
		return "not found";
	}
}
