package com.example.adambackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private String nameEvent;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean status;
    private Boolean isDelete;
    private Integer promotionId;
}
