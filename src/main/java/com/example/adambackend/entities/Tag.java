package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Column(name="is_deleted")
    private Boolean isDelete;
    @OneToMany(mappedBy = "tag")
    private List<TagProduct> tagProducts= new ArrayList<>();


}
