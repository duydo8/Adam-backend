package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Comment;
import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.response.CommentDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.CommentService;
import com.example.adambackend.service.DetailOrderService;
import com.example.adambackend.service.ProductSevice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("comment")
public class CommentWebsiteController {
	@Autowired
	private CommentService commentService;

	@Autowired
	private ProductSevice productSevice;

	@Autowired
	private AccountService accountService;

	@Autowired
	private DetailOrderService detailOrderService;


	@PostMapping("create")
	public ResponseEntity<?> createComment(@RequestBody Comment comment, @RequestParam("account_id") Integer accountId, @RequestParam("product_id") Integer productId) {
		List<Integer> listProductId = detailOrderService.findProductIdByOrder();
		for (Integer id : listProductId) {
			if (id == productId) {
				Optional<Product> productOptional = productSevice.findById(productId);
				Optional<Account> accountOptional = accountService.findById(accountId);
				if (comment.getVote() == 0 || productOptional.isPresent() || accountOptional.isPresent()) {
					return ResponseEntity.badRequest().body(new IGenericResponse(400, "can't create comment"));
				} else {
					int commentTotal = commentService.countCommentByProduct(productId);
					double voteAverage = productOptional.get().getVoteAverage();
					voteAverage = (voteAverage * commentTotal + comment.getVote()) / (commentTotal + 1);
					System.out.println(voteAverage);
					productOptional.get().setVoteAverage(voteAverage);
					productSevice.save(productOptional.get());
					return ResponseEntity.ok().body(new IGenericResponse(commentService.createCommentwithAccountIdAndProductId(comment.getContent(),
							LocalDateTime.now(), productId, accountId, 1, comment.getVote()), 200, "successfully"));
				}
			}
		}
		return ResponseEntity.badRequest().body(new IGenericResponse(400, "not contains in order"));
	}


	@DeleteMapping("delete")
	public ResponseEntity<?> deleteComment(@RequestParam("comment_id") Integer id) {
		try {
			Optional<Comment> comment = commentService.findById(id);
			if (comment.isPresent()) {
				Integer vote = comment.get().getVote();
				int commentTotal = commentService.countCommentByProduct(comment.get().getProduct().getId());

				double voteAverage = comment.get().getProduct().getVoteAverage();
				voteAverage = (voteAverage * commentTotal - vote) / (voteAverage - 1);
				Product product = comment.get().getProduct();
				product.setVoteAverage(voteAverage);
				productSevice.save(product);
				commentService.deleteById(id);
				return ResponseEntity.ok(new IGenericResponse(200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found comment"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findAllCommentByProductId")
	public ResponseEntity<?> findAllCommentByProductIdAndStatusIsActive(@RequestParam("product_id") Integer productId) {
		try {
			Optional<Product> product = productSevice.findById(productId);
			if (product.isPresent()) {
				return ResponseEntity.ok(new IGenericResponse(commentService.findAllCommentByProductId(productId), 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found Product"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findTop10CommentByProductId")
	public ResponseEntity<?> findTop10CommentByProductId(@RequestParam("productId") Integer productId) {
		try {
			Optional<Product> product = productSevice.findById(productId);
			if (product.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse(commentService.findTop10CommentByProductId(productId), 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found Product"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findAllByAccountIdAndProductId")
	public ResponseEntity<IGenericResponse> changeStatusComment(@RequestParam("account_id") Integer accountId, @RequestParam("product_id") Integer productId) {
		try {
			List<Comment> comments = commentService.findCommentByIdAccountAndIdProduct(accountId, productId);
			if (comments.size() > 0) {
				List<CommentDto> commentDtos = comments.stream().map(c -> new CommentDto(c)).collect(Collectors.toList());
				return ResponseEntity.ok().body(new IGenericResponse(commentDtos, 200, "find all comment successfully"));
			} else {
				return ResponseEntity.ok().body(new IGenericResponse(400, "not found comment by account id: " + accountId
						+ " product id: " + productId));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
