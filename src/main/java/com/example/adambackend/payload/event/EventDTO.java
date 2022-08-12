package com.example.adambackend.payload.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private String eventName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private Boolean type;
    private String image;


}
