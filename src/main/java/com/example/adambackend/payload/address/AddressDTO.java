package com.example.adambackend.payload.address;

import java.time.LocalDateTime;

public interface AddressDTO {
    Integer getId();

    String getAddressDetail();

    Integer getProvinceId();

    Integer getDistrictId();

    Integer getWardId();

    Boolean getIsDeleted();

    Boolean getIsActive();

    LocalDateTime getCreateDate();

    Boolean getIsDefault();

    String getPhoneNumber();

    String getFullName();
}
