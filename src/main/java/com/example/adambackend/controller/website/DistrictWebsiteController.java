package com.example.adambackend.controller.website;

import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.DistrictRepository;
import com.example.adambackend.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/district")
public class DistrictWebsiteController {
    @Autowired
    DistrictRepository districtRepository;
    @GetMapping("findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(new IGenericResponse<>(districtRepository.findAll(),200,""));
    }
}