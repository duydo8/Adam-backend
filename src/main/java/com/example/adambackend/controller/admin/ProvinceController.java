package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Province;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.ProvinceService;
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
@RequestMapping("admin/province")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @GetMapping("create")
    public ResponseEntity<?> createProvince(@RequestBody Province province) {
        try {
            return ResponseEntity.ok().body(new IGenericResponse<Province>(provinceService.save(province), 200, "successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateProvince(@RequestBody Province province) {
        try {
            Optional<Province> provinceOptional = provinceService.findById(province.getId());
            if (provinceOptional.isPresent()) {
                return ResponseEntity.ok().body(new IGenericResponse<Province>(provinceService.save(province), 200, "successfully"));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Province"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteProvince(@RequestParam("event_id") Integer id) {
        try {
            Optional<Province> provinceOptional = provinceService.findById(id);
            if (provinceOptional.isPresent()) {
                provinceService.deleteById(id);
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, "successfully"));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Province"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
