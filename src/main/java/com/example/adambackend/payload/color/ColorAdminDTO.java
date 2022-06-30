package com.example.adambackend.payload.color;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorAdminDTO {
    private Integer id;
    private String colorName;
    private Boolean isDeleted;
    private Boolean isActive;
    private LocalDateTime createDate;
}
