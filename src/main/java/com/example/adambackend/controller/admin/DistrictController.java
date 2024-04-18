package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.District;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/district")
public class DistrictController {
    @Autowired
    private DistrictService districtService;

    @GetMapping("create")
    public ResponseEntity<?> createWard(@RequestBody District district) {
        try {
            return ResponseEntity.ok().body(new IGenericResponse(districtService.save(district), 200, "successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody District district) {
        try {
            Optional<District> districtOptional = districtService.findById(district.getId());
            if (districtOptional.isPresent()) {

                return ResponseEntity.ok().body(new IGenericResponse(districtService.save(district), 200, "successfully"));
            } else {
                return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy Ward"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("event_id") Integer id) {
        try {
            Optional<District> districtOptional = districtService.findById(id);
            if (districtOptional.isPresent()) {
                districtService.deleteById(id);
                return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
            } else {
                return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy Ward"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
