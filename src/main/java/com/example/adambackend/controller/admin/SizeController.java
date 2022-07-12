package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Size;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.size.ListSizeIdDTO;
import com.example.adambackend.payload.size.SizeDTO;
import com.example.adambackend.payload.size.SizeUpdate;
import com.example.adambackend.repository.DetailProductRepository;
import com.example.adambackend.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/size")
public class SizeController {
    @Autowired
    SizeRepository sizeService;
    @Autowired
    DetailProductRepository detailProductRepository;

    @PostMapping("create")
    public ResponseEntity<?> createSize(@RequestBody SizeDTO sizeDTO) {
        try {
            Size size = new Size();
            size.setSizeName(sizeDTO.getSizeName());
            size.setCreateDate(LocalDateTime.now());
            size.setIsActive(true);
            size.setIsDeleted(false);
            return ResponseEntity.ok().body(new IGenericResponse<Size>(sizeService.save(size), 200, "success"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new HandleExceptionDemo(500, "can't duplicate name"));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody SizeUpdate sizeUpdate) {
        Optional<Size> size1 = sizeService.findById(sizeUpdate.getId());
        if (size1.isPresent()) {
            size1.get().setSizeName(sizeUpdate.getSizeName());
            size1.get().setIsActive(sizeUpdate.getIsActive());
            size1.get().setIsDeleted(sizeUpdate.getIsDeleted());
            return ResponseEntity.ok().body(new IGenericResponse<Size>(sizeService.save(size1.get()), 200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("size_id") Integer sizeId) {
        Optional<Size> size1 = sizeService.findById(sizeId);
        if (size1.isPresent()) {
            sizeService.deleteById(sizeId);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(new IGenericResponse<List<Size>>(sizeService.findAlls(), 200, ""));
    }

    @DeleteMapping("deleteByListId")
    public ResponseEntity<?> deleteArrayTagId(@RequestBody ListSizeIdDTO listSizeIdDTO) {
        List<Integer> list = listSizeIdDTO.getListSizeId();

        System.out.println(list.size());
        if (list.size() > 0) {
            for (Integer x : list
            ) {
                Optional<Size> sizeOptional = sizeService.findById(x);

                if (sizeOptional.isPresent()) {

                    sizeService.updateProductsDeleted(x);
                }
            }
            return ResponseEntity.ok().body(new IGenericResponse<>("", 200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));
    }
}
