package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Comment;
import com.example.adambackend.enums.CommentStatus;
import com.example.adambackend.payload.response.CommentDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("countCommentByAccountIdAndProductId")
    public ResponseEntity<IGenericResponse> countCommentByAccountIdAndProductId(@RequestParam("account_id") Long idAccount, @RequestParam("product_id") Long idProduct) {
        return ResponseEntity.ok().body(new IGenericResponse<Integer>(commentService.countCommentByAccountIdAndProductId(idAccount, idProduct), 200, "countCommentByAccountIdAndProductId"));
    }

    @PutMapping("changeStatusComment")
    public ResponseEntity<IGenericResponse> changeStatusComment(@RequestParam("comment_id") Long commentId,
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
            } else {
                return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "not found status"));
            }
            commentService.save(comment.get());
            CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
            return ResponseEntity.ok().body(new IGenericResponse<CommentDto>(commentDto, 200, "update successfully"));
        } else {
            return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "not found comment"));
        }
    }

    @GetMapping("findAllByAccountIdAndProductId")
    public ResponseEntity<IGenericResponse> changeStatusComment(@RequestParam("account_id") Long accountId, @RequestParam("product_id") Long productId) {
        List<Comment> comments = commentService.findCommentByIdAccountAndIdProduct(accountId, productId);
        if (comments.size() > 0) {
            List<CommentDto> commentDtos=comments.stream().map(c->new CommentDto(c.getId(),c.getContent(),c.getTimeCreated(),c.getCommentStatus())).collect(Collectors.toList());
            return ResponseEntity.ok().body(new IGenericResponse<List<CommentDto>>(commentDtos, 200, "find all comment successfully"));
        } else {
            return ResponseEntity.ok().body(new IGenericResponse(400, "not found comment by account id: " + accountId
                    + " product id: " + productId));
        }
    }
}
