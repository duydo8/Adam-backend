package com.example.adambackend.controller.website;

import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/ward")
public class WardWebsiteController {
    @Autowired
    WardRepository wardRepository;

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(new IGenericResponse<>(wardRepository.findAll(), 200, ""));
    }

    @GetMapping("findByDistrictId")
    public ResponseEntity<?> findByProviceId(@RequestParam("district_id") Integer districtId) {
        return ResponseEntity.ok().body(new IGenericResponse<>(wardRepository.findByDistrictId(districtId), 200, ""));

    }
}
