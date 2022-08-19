package com.example.adambackend.payload.product;

import java.time.LocalDateTime;

public interface ProductTop10Create {
     Integer getId();
     String getProductName();
     String getDescription();
     Boolean getIsDelete();
     String getImage();
     Double getVoteAverage();
     LocalDateTime getCreateDate();
     Boolean getIsComplete();
     Boolean getIsActive();
     Double getMinPrice();
     Double getMaxPrice();
}
