package com.example.adambackend.payload.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAdmin {
    List<OrderFindAllResponse> orders;
    private Integer totalElement;
}
