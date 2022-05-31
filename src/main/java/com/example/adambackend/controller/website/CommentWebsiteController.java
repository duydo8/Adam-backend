package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Comment;
import com.example.adambackend.entities.Product;
import com.example.adambackend.enums.CommentStatus;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.CommentDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.CommentService;
import com.example.adambackend.service.ProductSevice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("comment")
public class CommentWebsiteController {
    @Autowired
    CommentService commentService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProductSevice productSevice;

    @PostMapping("create")
    public ResponseEntity<?> createComment(@RequestParam("content") String content, @RequestParam("account_id") Long accountId, @RequestParam("product_id") Long productId) {
        return ResponseEntity.ok().body(new IGenericResponse<Comment>(commentService.createAccountwithAccountIdAndProductId(content,
                LocalDateTime.now(), productId, accountId, CommentStatus.PENDING), 200, ""));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateComment(@RequestParam("comment_id") Long id,
                                           @RequestParam("content") String content) {
        Optional<Comment> comment1 = commentService.findById(id);
        if (comment1.isPresent()) {
            comment1.get().setContent(content);
            comment1.get().setCommentStatus(CommentStatus.PENDING);
            comment1.get().setTimeCreated(LocalDateTime.now());
            return ResponseEntity.ok(new IGenericResponse<Comment>(commentService.save(comment1.get()), 200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found comment"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteComment(@RequestParam("comment_id") Long id) {
        Optional<Comment> comment1 = commentService.findById(id);
        if (comment1.isPresent()) {

            commentService.deleteById(id);
            return ResponseEntity.ok(new IGenericResponse(200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found comment"));
    }

    @GetMapping("findAllCommentByProductIdAndStatusIsActive")
    public ResponseEntity<?> findAllCommentByProductIdAndStatusIsActive(@RequestParam("product_id") Long productId) {
        Optional<Product> product = productSevice.findById(productId);
        if (product.isPresent()) {

            return ResponseEntity.ok(new IGenericResponse<List<Comment>>(commentService.findAllCommentByProductIdAndStatusIsActive(productId), 200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Product"));
    }

    @GetMapping("findTop10CommentByProductId")
    public ResponseEntity<?> findTop10CommentByProductId(@RequestParam("productId") Long productId) {
        Optional<Product> product = productSevice.findById(productId);
        if (product.isPresent()) {
            return ResponseEntity.ok().body(new IGenericResponse<List<Comment>>(commentService.findTop10CommentByProductId(productId), 200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Product"));
    }

    @GetMapping("findAllByAccountIdAndProductId")
    public ResponseEntity<IGenericResponse> changeStatusComment(@RequestParam("account_id") Long accountId, @RequestParam("product_id") Long productId) {
        List<Comment> comments = commentService.findCommentByIdAccountAndIdProduct(accountId, productId);
        if (comments.size() > 0) {
            List<CommentDto> commentDtos = comments.stream().map(c -> new CommentDto(c.getId(), c.getContent(), c.getTimeCreated(), c.getCommentStatus())).collect(Collectors.toList());
            return ResponseEntity.ok().body(new IGenericResponse<List<CommentDto>>(commentDtos, 200, "find all comment successfully"));
        } else {
            return ResponseEntity.ok().body(new IGenericResponse(400, "not found comment by account id: " + accountId
                    + " product id: " + productId));
        }
    }


}
