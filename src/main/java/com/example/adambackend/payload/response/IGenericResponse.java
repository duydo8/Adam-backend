package com.example.adambackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IGenericResponse<T> {
    private T entity;
    private int status;
    private String message;

    public IGenericResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
