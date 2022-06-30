package com.example.adambackend.payload.comment;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Product;
import com.example.adambackend.enums.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentAdminDTO {
    private Integer id;
    private String content;
    private Double vote;
//    private LocalDateTime timeCreated;
    private CommentStatus commentStatus;
//    private Integer accountId;
//
//    private Integer productId;

    private Boolean isActive;
}
