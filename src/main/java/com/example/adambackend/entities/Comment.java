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
@Table(name = "comments")
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String content;
	private Integer vote;
	private LocalDateTime timeCreated;
	private CommentStatus commentStatus;
	private Integer commentParentId;
	private Integer status;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
}
