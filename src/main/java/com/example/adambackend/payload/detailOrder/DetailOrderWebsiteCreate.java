package com.example.adambackend.payload.detailOrder;

import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.entities.Order;
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
public class DetailOrderWebsiteCreate {
    private Integer quantity;
    private Double price;
    private Integer detailProductId;
    private Integer orderId;

}
