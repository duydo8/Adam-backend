package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Province;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/province")
public class ProvinceController {
    @Autowired
    ProvinceService provinceService;

    @GetMapping("create")
    public ResponseEntity<?> createWard(@RequestBody Province province) {
        return ResponseEntity.ok().body(new IGenericResponse<Province>(provinceService.save(province), 200, ""));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody Province province) {
        Optional<Province> provinceOptional = provinceService.findById(province.getId());
        if (provinceOptional.isPresent()) {

            return ResponseEntity.ok().body(new IGenericResponse<Province>(provinceService.save(province), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Ward"));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("event_id") Integer id) {
        Optional<Province> provinceOptional = provinceService.findById(id);
        if (provinceOptional.isPresent()) {
            provinceService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Ward"));
        }
    }
}
