package com.example.adambackend.payload;

import com.example.adambackend.entities.Promotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
