package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Ward;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("admin/ward")
public class WardController {
    @Autowired
    private WardService wardService;

    @GetMapping("create")
    public ResponseEntity<?> createWard(@RequestBody Ward ward) {
        try {
            return ResponseEntity.ok().body(new IGenericResponse<Ward>(wardService.save(ward), 200, "successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody Ward ward) {
        try {
            Optional<Ward> wardOptional = wardService.findById(ward.getId());
            if (wardOptional.isPresent()) {
                return ResponseEntity.ok().body(new IGenericResponse<Ward>(wardService.save(ward), 200, "successfully"));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Ward"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("ward_id") Integer id) {
        try {
            Optional<Ward> wardOptional = wardService.findById(id);
            if (wardOptional.isPresent()) {
                wardService.deleteById(id);
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, "successfully"));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Ward"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
