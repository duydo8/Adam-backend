package com.example.adambackend.payload.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdateDTO {
    private Integer id;
    private String eventName;
    private String image;
    private Boolean isActive;
}
