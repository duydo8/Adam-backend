package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Material;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.MaterialDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.MaterialService;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/material")
public class MaterialControler {
    @Autowired
    MaterialService materialService;
    @Autowired
    ProductSevice productSevice;

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(new IGenericResponse<>(materialService.findAll(), 200, ""));
    }

    @PostMapping("create")
    public ResponseEntity<?> createWard(@RequestBody MaterialDTO materialDTO) {
        try {
            Material material = new Material();
            material.setMaterialName(materialDTO.getMaterialName());
            material.setIsActive(true);
            material.setCreateDate(LocalDateTime.now());
            material.setIsDeleted(false);
            return ResponseEntity.ok().body(new IGenericResponse<Material>(materialService.save(material), 200, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new HandleExceptionDemo(500, "can't duplicate name"));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody Material material) {
        Optional<Material> materialOptional = materialService.findById(material.getId());
        if (materialOptional.isPresent()) {

            return ResponseEntity.ok().body(new IGenericResponse<Material>(materialService.save(material), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Ward"));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("material_id") Integer id) {
        Optional<Material> materialOptional = materialService.findById(id);
        if (materialOptional.isPresent()) {
            materialService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Ward"));
        }
    }


}
