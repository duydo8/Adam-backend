package com.example.adambackend.payload.order;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Address;
import com.example.adambackend.payload.address.AddressResponse;
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
public class OrderFindAllResponse {
    private Integer id;
    private Integer status;

    private LocalDateTime createDate;

    private Account account;

    private String fullName;

    private String phoneNumber;

    private Double amountPrice;

    private Double salePrice;

    private Double totalPrice;

    private AddressResponse address;


    private String addressDetail;

    private String orderCode;
}
