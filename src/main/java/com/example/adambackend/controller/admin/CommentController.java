package com.example.adambackend.controller.admin;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Comment;
import com.example.adambackend.payload.comment.CommentDTO;
import com.example.adambackend.payload.comment.ListCommentId;
import com.example.adambackend.payload.response.CommentDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping("countCommentByAccountIdAndProductId")
	public ResponseEntity<IGenericResponse> countCommentByAccountIdAndProductId(@RequestParam("account_id") Integer idAccount, @RequestParam("product_id") Integer idProduct) {
		try {
			return ResponseEntity.ok().body(new IGenericResponse(commentService.countCommentByAccountIdAndProductId(idAccount
					, idProduct), 200, "countCommentByAccountIdAndProductId"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("changeStatusComment")
	public ResponseEntity<?> changeStatusComment(@RequestParam("comment_id") Integer commentId,
												 @RequestParam("status") int status) {
		try {
			Optional<Comment> comment = commentService.findById(commentId);
			if (comment.isPresent()) {
				comment.get().setCommentStatus(status);
				commentService.save(comment.get());
				return ResponseEntity.ok().body(new IGenericResponse(commentService.save(comment.get()), 200, "update successfully"));
			} else {
				return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found comment"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findAllByAccountIdAndProductId")
	public ResponseEntity<?> findAllByAccountIdAndProductId(@RequestParam("account_id") Integer accountId, @RequestParam("product_id") Integer productId) {
		try {
			List<Comment> comments = commentService.findCommentByIdAccountAndIdProduct(accountId, productId);
			if (comments.size() > 0) {
				List<CommentDto> commentDtoList = comments.stream().map(c -> new CommentDto(c)).collect(Collectors.toList());
				return ResponseEntity.ok().body(new IGenericResponse(commentDtoList, 200, "find all comment successfully"));
			} else {
				return ResponseEntity.ok().body(new IGenericResponse(400, "not found comment by account id: " + accountId
						+ " product id: " + productId));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(new IGenericResponse<>(commentService.findAll(), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody CommentDTO commentDTO) {
		try {
			Comment comment = commentService.update(commentDTO);
			if (CommonUtil.isNotNull(comment)) {
				return ResponseEntity.ok().body(new IGenericResponse(comment, 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found comment"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> delete(@RequestParam("id") Integer Id) {
		try {
			Optional<Comment> commentOptional = commentService.findById(Id);
			if (commentOptional.isPresent()) {
				commentService.deleteById(Id);
				return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found comment"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("deleteByListId")
	public ResponseEntity<?> deleteListId(@RequestBody ListCommentId listCommentId) {
		try {
			String message = commentService.updateCommentDeleted(listCommentId.getListId());
			return ResponseEntity.ok().body(new IGenericResponse(200, message));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

}
