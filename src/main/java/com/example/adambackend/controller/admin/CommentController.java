package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Comment;
import com.example.adambackend.enums.CommentStatus;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.comment.CommentAdminDTO;
import com.example.adambackend.payload.comment.CommentAdminUpdate;
import com.example.adambackend.payload.response.CommentDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.CommentService;
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
@RequestMapping("admin/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("countCommentByAccountIdAndProductId")
    public ResponseEntity<IGenericResponse> countCommentByAccountIdAndProductId(@RequestParam("account_id") Integer idAccount, @RequestParam("product_id") Integer idProduct) {
        return ResponseEntity.ok().body(new IGenericResponse<Integer>(commentService.countCommentByAccountIdAndProductId(idAccount, idProduct), 200, "countCommentByAccountIdAndProductId"));
    }

    @PutMapping("changeStatusComment")
    public ResponseEntity<?> changeStatusComment(@RequestParam("comment_id") Integer commentId,
                                                 @RequestParam("status") String status) {
        //Comment comment= commentService.findCommentByIdAccountAndIdProduct(idAccount,idProduct);
        Optional<Comment> comment = commentService.findById(commentId);
        if (comment.isPresent()) {
            if (status.equalsIgnoreCase(String.valueOf(CommentStatus.ACTIVE))) {
                comment.get().setCommentStatus(CommentStatus.ACTIVE);
            } else if (status.equalsIgnoreCase(String.valueOf(CommentStatus.PENDING))) {
                comment.get().setCommentStatus(CommentStatus.PENDING);
            } else if (status.equalsIgnoreCase(String.valueOf(CommentStatus.DELETE))) {
                comment.get().setCommentStatus(CommentStatus.DELETE);
            } else if (status.equalsIgnoreCase(String.valueOf(CommentStatus.UPDATED))) {
                comment.get().setCommentStatus(CommentStatus.UPDATED);
            } else if (status.equalsIgnoreCase(String.valueOf(CommentStatus.UPDATED_ACTIVE))) {
                comment.get().setCommentStatus(CommentStatus.UPDATED_ACTIVE);
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found status"));
            }
            commentService.save(comment.get());
            CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
            return ResponseEntity.ok().body(new IGenericResponse<CommentDto>(commentDto, 200, "update successfully"));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found comment"));
        }
    }

    @GetMapping("findAllByAccountIdAndProductId")
    public ResponseEntity<?> changeStatusComment(@RequestParam("account_id") Integer accountId, @RequestParam("product_id") Integer productId) {
        List<Comment> comments = commentService.findCommentByIdAccountAndIdProduct(accountId, productId);
        if (comments.size() > 0) {
            List<CommentDto> commentDtos = comments.stream().map(c -> new CommentDto(c.getId(), c.getContent(), c.getTimeCreated(), c.getCommentStatus())).collect(Collectors.toList());
            return ResponseEntity.ok().body(new IGenericResponse<List<CommentDto>>(commentDtos, 200, "find all comment successfully"));
        } else {
            return ResponseEntity.ok().body(new HandleExceptionDemo(400, "not found comment by account id: " + accountId
                    + " product id: " + productId));
        }
    }


    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(new IGenericResponse<>(commentService.findAll(), 200, ""));
    }

    @Autowired
    AccountService accountService;
    @Autowired
    ProductSevice productService;



    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody CommentAdminDTO commentAdminDTO) {
        Optional<Comment> commentOptional = commentService.findById(commentAdminDTO.getId());
        if (commentOptional.isPresent()) {
            Comment comment=commentOptional.get();
            comment.setVote(commentAdminDTO.getVote());
            comment.setCommentStatus(commentAdminDTO.getCommentStatus());
        //    comment.setAccount(accountService.findById(commentAdminDTO.getAccountId()).get());
       //     comment.setProduct(productService.findById(commentAdminDTO.getProductId()).get());
            comment.setIsActive(commentAdminDTO.getIsActive());
            comment.setContent(commentAdminDTO.getContent());
      //      comment.setTimeCreated(commentAdminDTO.getTimeCreated());


            commentService.save(comment);
            CommentAdminUpdate commentAdminUpdate= new CommentAdminUpdate(commentAdminDTO.getId(),
                    commentAdminDTO.getContent(), commentAdminDTO.getVote(),
                    comment.getTimeCreated(),commentAdminDTO.getCommentStatus(),comment.getAccount().getId(),
                    comment.getProduct().getId(),commentAdminDTO.getIsActive());
            return ResponseEntity.ok().body(new IGenericResponse<>(commentAdminUpdate, 200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("id") Integer Id) {
        Optional<Comment> commentOptional = commentService.findById(Id);
        if (commentOptional.isPresent()) {
            commentService.deleteById(Id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

//    @DeleteMapping("deleteByListId")
//    public ResponseEntity<?> deleteArrayTagId(@RequestBody ListColorIdDTO listColorIdDTO) {
//        List<Integer> list = listColorIdDTO.getListColorId();
//        System.out.println(list.size());
//        if (list.size() > 0) {
//            for (Integer x : list
//            ) {
//                Optional<Color> colorOptional = colorService.findById(x);
//
//                if (colorOptional.isPresent()) {
//
//                    colorService.deleteById(x);
//
//                }
//            }
//            return ResponseEntity.ok().body(new IGenericResponse<>("", 200, ""));
//        }
//        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, " not found"));
//    }

}
