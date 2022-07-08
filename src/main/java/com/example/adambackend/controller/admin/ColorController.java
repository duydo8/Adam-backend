package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Color;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.color.ColorAdminDTO;
import com.example.adambackend.payload.color.ColorDTO;
import com.example.adambackend.payload.color.ColorUpdate;
import com.example.adambackend.payload.color.ListColorIdDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.ColorRepository;
import com.example.adambackend.repository.DetailProductRepository;
import com.example.adambackend.service.ColorService;
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
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(new IGenericResponse<List<Color>>(colorService.findAlls(), 200, ""));
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
        Optional<Color> colorOptional = colorService.findById(colorUpdate.getId());
        if (colorOptional.isPresent()) {
            colorOptional.get().setColorName(colorUpdate.getColorName());
            colorOptional.get().setIsActive(colorUpdate.getIsActive());
            colorOptional.get().setIsDeleted(colorUpdate.getIsDeleted());

            return ResponseEntity.ok().body(new IGenericResponse<>(colorService.save(colorOptional.get()), 200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("color_id") Integer colorId) {
        Optional<Color> colorOptional = colorService.findById(colorId);
        if (colorOptional.isPresent()) {
            colorService.deleteById(colorId);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @DeleteMapping("deleteByListId")
    public ResponseEntity<?> deleteArrayTagId(@RequestBody ListColorIdDTO listColorIdDTO) {
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
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, " not found"));
    }
}
