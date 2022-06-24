package com.example.adambackend.payload;

import com.example.adambackend.entities.Category;
import com.example.adambackend.entities.MaterialProduct;
import com.example.adambackend.entities.TagProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {
    private Integer id;
    private String productName;
    private String description;
    private Boolean isDelete;
    private String image;
    private Double voteAverage;
    private LocalDateTime createDate;
    private Integer categoryId;
    private List<Integer> tagProducts = new ArrayList<>();
    private List<Integer> materialProducts = new ArrayList<>();
    private Boolean isActive;
}
