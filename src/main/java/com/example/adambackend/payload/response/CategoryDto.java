package com.example.adambackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private  int id;
    private String categoryName;
    private boolean isDeleted;
    private Integer categoryParentId;
}
