package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Comment;
import com.example.adambackend.entities.Product;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.CommentDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.CommentService;
import com.example.adambackend.service.DetailOrderService;
import com.example.adambackend.service.ProductSevice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteComment(@RequestParam("comment_id") Integer id) {
        try {
            Optional<Comment> comment1 = commentService.findById(id);
            if (comment1.isPresent()) {
                Integer vote = comment1.get().getVote();
                int commentTotal = commentService.countCommentByProduct(comment1.get().getProduct().getId());

                double voteAverage = comment1.get().getProduct().getVoteAverage();
                voteAverage = (voteAverage * commentTotal - vote) / (voteAverage - 1);
                Product p = comment1.get().getProduct();
                p.setVoteAverage(voteAverage);
                productSevice.save(p);
                commentService.deleteById(id);
                return ResponseEntity.ok(new IGenericResponse(200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy comment"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findAllCommentByProductIdAndStatusIsActive")
    public ResponseEntity<?> findAllCommentByProductIdAndStatusIsActive(@RequestParam("product_id") Integer productId) {
        try {
            Optional<Product> product = productSevice.findById(productId);
            if (product.isPresent()) {

                return ResponseEntity.ok(new IGenericResponse<>(commentService.findAllCommentByProductIdAndStatusIsActive(productId), 200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Product"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findTop10CommentByProductId")
    public ResponseEntity<?> findTop10CommentByProductId(@RequestParam("productId") Integer productId) {
        try {
            Optional<Product> product = productSevice.findById(productId);
            if (product.isPresent()) {
                return ResponseEntity.ok().body(new IGenericResponse<>(commentService.findTop10CommentByProductId(productId), 200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Product"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findAllByAccountIdAndProductId")
    public ResponseEntity<IGenericResponse> changeStatusComment(@RequestParam("account_id") Integer accountId, @RequestParam("product_id") Integer productId) {
        try {
            List<Comment> comments = commentService.findCommentByIdAccountAndIdProduct(accountId, productId);
            if (comments.size() > 0) {
                List<CommentDto> commentDtos = comments.stream().map(c -> new CommentDto(c.getId(), c.getContent(), c.getTimeCreated(), c.getCommentStatus())).collect(Collectors.toList());
                return ResponseEntity.ok().body(new IGenericResponse<>(commentDtos, 200, "find all comment successfully"));
            } else {
                return ResponseEntity.ok().body(new IGenericResponse(400, "Không tìm thấy comment by account id: " + accountId
                        + " product id: " + productId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }


}
