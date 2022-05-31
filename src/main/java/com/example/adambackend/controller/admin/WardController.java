package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Ward;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("admin/ward")
public class WardController {
    @Autowired
    WardService wardService;

    @GetMapping("create")
    public ResponseEntity<?> createWard(@RequestBody Ward ward) {
        return ResponseEntity.ok().body(new IGenericResponse<Ward>(wardService.save(ward), 200, ""));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody Ward ward) {
        Optional<Ward> wardOptional = wardService.findById(ward.getId());
        if (wardOptional.isPresent()) {

            return ResponseEntity.ok().body(new IGenericResponse<Ward>(wardService.save(ward), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Ward"));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("event_id") Long id) {
        Optional<Ward> wardOptional = wardService.findById(id);
        if (wardOptional.isPresent()) {
            wardService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Ward"));
        }
    }
}
