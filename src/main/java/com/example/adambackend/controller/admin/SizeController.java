package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Size;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("admin/size")
public class SizeController {
    @Autowired
    SizeService sizeService;
    @PostMapping("create")
    public ResponseEntity<?> createSize(@RequestBody Size size){

        return ResponseEntity.ok().body(new IGenericResponse<Size>(sizeService.save(size),200,"success"));
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody Size size){
        Optional<Size> size1= sizeService.findById(size.getId());
        if(size1.isPresent()){
            return ResponseEntity.ok().body(new IGenericResponse<Size>(sizeService.save(size),200,"success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found"));
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("size_id")Integer sizeId){
        Optional<Size> size1= sizeService.findById(sizeId);
        if(size1.isPresent()){
            sizeService.deleteById(sizeId);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200,"success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found"));
    }
}
