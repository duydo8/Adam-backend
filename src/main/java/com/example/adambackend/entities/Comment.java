package com.example.adambackend.entities;

import com.example.adambackend.enums.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comments")
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Column(name="time_create")
    private LocalDateTime timeCreated;
    @Column(name="status")
    private CommentStatus commentStatus;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}