package com.example.adambackend.payload.productWebsiteDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public interface ProductHandleValue {
     Integer getId();
     String getDescription();
     Boolean getIsActive();
     Double getMaxPrice();
     Double getMinPrice();
     String getProductName();
}
