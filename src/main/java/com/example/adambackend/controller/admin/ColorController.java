package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Color;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.color.ColorDTO;
import com.example.adambackend.payload.color.ColorUpdate;
import com.example.adambackend.payload.color.ListColorIdDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.ColorRepository;
import com.example.adambackend.repository.DetailProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/color")
public class ColorController {
    @Autowired
    ColorRepository colorService;
    @Autowired
    DetailProductRepository detailProductRepository;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("findAll")
    public ResponseEntity<?> findAll(@RequestParam("name")String name) {
        try {
            if(name==null){
                return ResponseEntity.ok().body(new IGenericResponse<>(colorService.findAll(), 200, ""));

            } return ResponseEntity.ok().body(new IGenericResponse<>(colorService.findAll(name), 200, ""));

           } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }


    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody ColorDTO colorDTO) {
        try {
            Color color = new Color();
            color.setColorName(colorDTO.getColorName());
            color.setIsDeleted(false);
            color.setCreateDate(LocalDateTime.now());
            color.setIsActive(true);
            return ResponseEntity.ok().body(new IGenericResponse<Color>(colorService.save(color), 200, "success"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new HandleExceptionDemo(500, "can't duplicate name"));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody ColorUpdate colorUpdate) {
        try {
            Optional<Color> colorOptional = colorService.findById(colorUpdate.getId());
            if (colorOptional.isPresent()) {
                colorOptional.get().setColorName(colorUpdate.getColorName());
                colorOptional.get().setIsActive(colorUpdate.getIsActive());
                colorOptional.get().setIsDeleted(colorUpdate.getIsDeleted());

                return ResponseEntity.ok().body(new IGenericResponse<>(colorService.save(colorOptional.get()), 200, "success"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("color_id") Integer colorId) {
        try {
            Optional<Color> colorOptional = colorService.findById(colorId);
            if (colorOptional.isPresent()) {
                colorService.deleteById(colorId);
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("deleteByListId")
    public ResponseEntity<?> deleteArrayTagId(@RequestBody ListColorIdDTO listColorIdDTO) {
        try {
            List<Integer> list = listColorIdDTO.getListColorId();
            System.out.println(list.size());
            if (list.size() > 0) {
                for (Integer x : list
                ) {
                    Optional<Color> colorOptional = colorService.findById(x);

                    if (colorOptional.isPresent()) {

                        colorService.updateColorsDeleted(x);

                    }
                }
                return ResponseEntity.ok().body(new IGenericResponse<>("", 200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, " Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
