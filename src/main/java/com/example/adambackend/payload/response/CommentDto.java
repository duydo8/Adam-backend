package com.example.adambackend.payload.response;

import com.example.adambackend.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	private Integer id;
	private String content;
	private LocalDateTime createDate;
	private Integer commentStatus;

	public CommentDto(Comment comment){
		this.id = comment.getId();
		this.content = comment.getContent();
		this.createDate = comment.getCreateDate();
		this.commentStatus = comment.getCommentStatus();
	}
}
