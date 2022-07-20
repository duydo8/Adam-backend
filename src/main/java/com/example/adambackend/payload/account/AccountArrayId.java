package com.example.adambackend.payload.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountArrayId {
    private List<Integer> accountIdList;
}
