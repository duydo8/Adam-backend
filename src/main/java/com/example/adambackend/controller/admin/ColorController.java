package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Category;
import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Size;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.ColorService;
import com.example.adambackend.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin/color")
public class ColorController {
    @Autowired
    ColorService colorService;
    @GetMapping("findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(new IGenericResponse<List<Color>>(colorService.findAll(),200,""));
    }
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody Color color){

        return ResponseEntity.ok().body(new IGenericResponse<Color>(colorService.save(color),200,"success"));
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody Color color){
        Optional<Color> colorOptional= colorService.findById(color.getId());
        if(colorOptional.isPresent()){
            return ResponseEntity.ok().body(new IGenericResponse<Color>(colorService.save(color),200,"success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found"));
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("color_id")Long colorId){
        Optional<Color> colorOptional= colorService.findById(colorId);
        if(colorOptional.isPresent()){
            colorService.deleteById(colorId);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200,"success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found"));
    }
}