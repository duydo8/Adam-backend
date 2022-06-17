package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.District;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/district")
public class DistrictController {
    @Autowired
    DistrictService districtService;

    @GetMapping("create")
    public ResponseEntity<?> createWard(@RequestBody District district) {
        return ResponseEntity.ok().body(new IGenericResponse<District>(districtService.save(district), 200, ""));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody District district) {
        Optional<District> districtOptional = districtService.findById(district.getId());
        if (districtOptional.isPresent()) {

            return ResponseEntity.ok().body(new IGenericResponse<District>(districtService.save(district), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Ward"));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("event_id") Integer id) {
        Optional<District> districtOptional = districtService.findById(id);
        if (districtOptional.isPresent()) {
            districtService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Ward"));
        }
    }
}
