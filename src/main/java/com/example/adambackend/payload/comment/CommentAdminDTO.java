package com.example.adambackend.payload.comment;

import com.example.adambackend.enums.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentAdminDTO {
	private Integer id;
	private String content;
	private Integer vote;
	private CommentStatus commentStatus;
	private Integer accountId;
	private Integer productId;
	private Integer status;
}
