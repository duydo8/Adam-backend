package com.example.adambackend.payload.address;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.District;
import com.example.adambackend.entities.Province;
import com.example.adambackend.entities.Ward;
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
public class AddressWebsiteCreate {
    private String addressDetail;
    private Integer accountId;
    private Integer provinceId;
    private Integer districtId;
    private Integer wardId;
}
