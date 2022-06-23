package com.example.adambackend.entities;

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
@Table(name = "sizes")
@Entity
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "size_name")
    private String sizeName;
    @Column(name="is_deleted")
    private Boolean isDeleted;
    @Column(name="is_active")
    private Boolean isActive;
    @JsonIgnore
    @OneToMany(mappedBy = "size")
    private List<DetailProduct> detailProducts = new ArrayList<>();
    @Column(name="create_date")
    private LocalDateTime createDate;
}
