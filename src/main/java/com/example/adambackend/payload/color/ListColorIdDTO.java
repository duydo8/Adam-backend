package com.example.adambackend.payload.color;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListColorIdDTO {
    List<Integer> listColorId;
}
