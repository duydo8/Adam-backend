package com.example.adambackend.payload.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
	private Integer id;
	private String content;
	private Integer vote;
	private Integer commentStatus;
	private Integer accountId;
	private Integer productId;
	private Integer status;
}
