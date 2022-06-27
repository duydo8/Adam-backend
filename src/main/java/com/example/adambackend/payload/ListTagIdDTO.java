package com.example.adambackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ListTagIdDTO {
    List<Integer> tagIdList;
}
