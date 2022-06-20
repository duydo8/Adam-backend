package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Comment;
import com.example.adambackend.entities.Product;
import com.example.adambackend.enums.CommentStatus;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("/*")
@RequestMapping("comment")
public class CommentWebsiteController {
    @Autowired
    CommentService commentService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProductSevice productSevice;
    @Autowired
    AccountService accountService;
    @Autowired
    DetailOrderService detailOrderService;

//
    @PostMapping("create")
    public ResponseEntity<?> createComment(@RequestBody Comment comment, @RequestParam("account_id") Integer accountId, @RequestParam("product_id") Integer productId) {
//        if(productId)
        List<Integer> listProductId= detailOrderService.findProductIdByOrder();
        for (Integer id: listProductId
             ) {
            if(id==productId){
                Optional<Product> productOptional= productSevice.findById(productId);
                Optional<Account> accountOptional= accountService.findById(accountId);
                if(comment.getVote()==0||productOptional.isPresent()||accountOptional.isPresent() ){
                    return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"can't create comment"));
                }else{
                    int commentTotal= commentService.countCommentByProduct(productId);

                    double voteAverage= productOptional.get().getVoteAverage();
                    voteAverage=(voteAverage*commentTotal+comment.getVote())/(commentTotal+1);
                    System.out.println(voteAverage);
                    productOptional.get().setVoteAverage(voteAverage);
                    productSevice.save(productOptional.get());
                    return ResponseEntity.ok().body(new IGenericResponse<Comment>(commentService.createAccountwithAccountIdAndProductId(comment.getContent(),

                            LocalDateTime.now(), productId, accountId, CommentStatus.PENDING,comment.getVote()), 200, ""));
                }
            }
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not contains in order"));
}
    @PutMapping("update")
    public ResponseEntity<?> updateComment(@RequestParam("comment_id") Integer id,
                                           @RequestParam("content") String content) {
        Optional<Comment> comment1 = commentService.findById(id);
        if (comment1.isPresent()) {
            comment1.get().setContent(content);
            comment1.get().setCommentStatus(CommentStatus.UPDATED);
            comment1.get().setTimeCreated(LocalDateTime.now());
            return ResponseEntity.ok(new IGenericResponse<Comment>(commentService.save(comment1.get()), 200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found comment"));
    }
//
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteComment(@RequestParam("comment_id") Integer id) {
        Optional<Comment> comment1 = commentService.findById(id);
        if (comment1.isPresent()) {
            int vote= comment1.get().getVote();
            int commentTotal= commentService.countCommentByProduct(comment1.get().getProduct().getId());

            double voteAverage= comment1.get().getProduct().getVoteAverage();
            voteAverage=(voteAverage*commentTotal-vote)/(voteAverage-1);
            Product p = comment1.get().getProduct();
            p.setVoteAverage(voteAverage);
            productSevice.save(p);
            commentService.deleteById(id);
            return ResponseEntity.ok(new IGenericResponse(200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found comment"));
    }

    @GetMapping("findAllCommentByProductIdAndStatusIsActive")
    public ResponseEntity<?> findAllCommentByProductIdAndStatusIsActive(@RequestParam("product_id") Integer productId) {
        Optional<Product> product = productSevice.findById(productId);
        if (product.isPresent()) {

            return ResponseEntity.ok(new IGenericResponse<List<Comment>>(commentService.findAllCommentByProductIdAndStatusIsActive(productId), 200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Product"));
    }

    @GetMapping("findTop10CommentByProductId")
    public ResponseEntity<?> findTop10CommentByProductId(@RequestParam("productId") Integer productId) {
        Optional<Product> product = productSevice.findById(productId);
        if (product.isPresent()) {
            return ResponseEntity.ok().body(new IGenericResponse<List<Comment>>(commentService.findTop10CommentByProductId(productId), 200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Product"));
    }

    @GetMapping("findAllByAccountIdAndProductId")
    public ResponseEntity<IGenericResponse> changeStatusComment(@RequestParam("account_id") Integer accountId, @RequestParam("product_id") Integer productId) {
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
