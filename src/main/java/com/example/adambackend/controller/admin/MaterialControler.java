package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Material;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.material.ListMaterialIdDTO;
import com.example.adambackend.payload.material.MaterialDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.MaterialProductRepository;
import com.example.adambackend.repository.MaterialRepository;
import com.example.adambackend.service.MaterialService;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/material")
public class MaterialControler {
    @Autowired
    MaterialRepository materialService;
    @Autowired
    ProductSevice productSevice;
    @Autowired
    MaterialProductRepository materialProductRepository;

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(new IGenericResponse<>(materialService.findAlls(), 200, ""));
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
    public ResponseEntity<?> updateEvent(@RequestBody Material  material) {
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

    @DeleteMapping("deleteByListId")
    public ResponseEntity<?> deleteArrayTagId(@RequestBody ListMaterialIdDTO listMaterialIdDTO) {
        List<Integer> listMaterialIdDTOx = listMaterialIdDTO.getListMaterialId();

        System.out.println(listMaterialIdDTOx.size());
        if (listMaterialIdDTOx.size() > 0) {
            for (Integer x : listMaterialIdDTOx
            ) {
                Optional<Material> materialOptional = materialService.findById(x);
                if (materialOptional.isPresent()) {
materialProductRepository.updateMaterialProductsDeleted(x);
                    materialService.updateDeleteByArrayId(x);

                }
            }
            return ResponseEntity.ok().body(new IGenericResponse<>("", 200, ""));

        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));
    }


}
