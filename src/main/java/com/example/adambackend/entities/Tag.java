package com.example.adambackend.entities;

import com.example.adambackend.payload.tag.TagDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
@Entity
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "tag_name")
	private String tagName;
	private Integer status;
	@Column(name = "create_date")
	private LocalDateTime createDate;

	@JsonIgnore
	@OneToMany(mappedBy = "tag")
	private List<TagProduct> tagProducts = new ArrayList<>();


	public Tag(TagDTO tagDTO){
		this.createDate = LocalDateTime.now();
		this.status = 1;
		this.tagName = tagDTO.getTagName();
	}
}
